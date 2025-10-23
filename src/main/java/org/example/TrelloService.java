    package org.example;

    import com.google.gson.reflect.TypeToken;
    import com.julienvey.trello.Trello;
    import com.julienvey.trello.domain.*;
    import com.julienvey.trello.impl.TrelloImpl;
    import com.julienvey.trello.impl.http.ApacheHttpClient;
    import org.example.dto.CardDto;
    import org.example.dto.CheckListDto;
    import org.example.model.Lesson;
    import org.example.model.Subject;

    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.io.InputStream;
    import java.lang.reflect.Type;
    import java.time.ZoneId;
    import java.util.*;

    public class TrelloService {
        private final Trello trelloApi;
        private final String TRELLO_KEY;
        private final String TRELLO_TOKEN;
        private final ApiClient trelloApiClient = new ApiClient("https://api.trello.com/1");

        public TrelloService() throws IOException {
            Properties properties = new Properties();
            try (InputStream input = Main.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    throw new FileNotFoundException("application.properties not found in classpath");
                }
                properties.load(input);
            }

            TRELLO_KEY = properties.getProperty("trelloKey");
            TRELLO_TOKEN = properties.getProperty("trelloToken");
            trelloApi = new TrelloImpl(TRELLO_KEY, TRELLO_TOKEN, new ApacheHttpClient());
        }

        public void createLabCards(List<Subject> subjects, int subgroupNumber, String boardId, String listId) throws IOException {
            Map<String, List<String>> labsBySubject = getIncompleteLabNames(boardId);

            for (Subject subject : subjects) {
                String subjectName = subject.getName();
                List<String> labNames = labsBySubject.getOrDefault(subjectName, List.of());
                List<Lesson> lessons = subject.getLessons()
                                            .stream()
                                            .filter(lesson -> lesson.getLessonType() == LessonType.LABORATORY &&
                                                    (lesson.getNumSubgroup() == 0 || lesson.getNumSubgroup() == subgroupNumber))
                                            .toList();

                for (int i = 0; i < lessons.size(); i++) {
                    Lesson lesson = lessons.get(i);
                    String labName = (i < labNames.size()) ? labNames.get(i) : "ЛабX";

                    createLabCard(lesson, subjectName, labName, listId);
                }
            }
        }

        public Card createLabCard(Lesson lesson, String subjectName, String labName, String listId) {
            String name = labName + " (" + subjectName + ")";
            Date due = Date.from(lesson.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

            Card card = new Card();
            card.setName(name);
            card.setDue(due);
            return trelloApi.createCard(listId, card);
        }

        public TList getListById(String listId) throws IOException {
            TList list = trelloApi.getList(listId);
            updateTList(list);
            return list;
        }

        public TList getListByName(String name, String boardId) throws IOException {
            Board board = trelloApi.getBoard(boardId);
            List<TList> lists = board.fetchLists();
            TList tList = lists.stream()
                    .filter(list -> Objects.equals(list.getName(), name))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("List with name " + name + " not found"));
            updateTList(tList);
            return tList;
        }

        public List<Card> findListCards(String listId) throws IOException {
            Type listCardDtoType = new TypeToken<List<CardDto>>(){}.getType();
            List<CardDto> cardDtoList = trelloApiClient.get
                    (
                            "/lists/" + listId + "/cards",
                            listCardDtoType,
                            Map.of("key", TRELLO_KEY, "token", TRELLO_TOKEN)
                    );
            return cardDtoList
                    .stream()
                    .map(CardDto::createCard)
                    .toList();
        }

        public void updateTList(TList list) throws IOException {
            list.setInternalTrello(trelloApi);
            list.setCards(findListCards(list.getId()));
        }

        public Map<String, List<String>> getIncompleteLabNames(String boardId) throws IOException {
            String listName = "Количество лаб";
            Map<String, List<String>> labsBySubject = new HashMap<>();

            TList labCountList = getListByName(listName, boardId);
            updateTList(labCountList);

            for (Card card : labCountList.getCards()){
                processCard(card, labsBySubject);
            }

            return labsBySubject;
        }

        private void processCard(Card card, Map<String, List<String>> labsBySubject) throws IOException {
            String subjectName = extractSubjectName(card.getName());

            for (String checkListId : card.getIdChecklists()) {
                CheckList checkList = getCheckListById(checkListId);
                List<String> incompleteLabNames = getIncompleteLabNames(checkList);
                labsBySubject.computeIfAbsent(subjectName, k -> new ArrayList<>()).addAll(incompleteLabNames);
            }
        }

        public String extractSubjectName(String cardName) {
            String splitString = cardName.split("\\(")[1];
            return splitString.substring(0, splitString.length() - 1);
        }

        public List<String> getIncompleteLabNames(CheckList checkList) {
            return checkList.getCheckItems()
                    .stream()
                    .filter(item -> item.getState().equals("incomplete"))
                    .map(CheckItem::getName)
                    .toList();
        }

        public CheckList getCheckListById(String checkListId) throws IOException {
            CheckListDto checkListDto = trelloApiClient.get
                    (
                    "/checklists/" + checkListId,
                    CheckListDto.class,
                    Map.of("key", TRELLO_KEY, "token", TRELLO_TOKEN)
                    );

                return checkListDto.createCheckList();
        }
    }
