package org.example.service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TrelloService {
//    private final TrelloApiClient apiClient;
//
//    public void createLabCards(List<Subject> subjects, int subgroupNumber, String boardId, String listId) throws IOException {
//        Map<String, List<String>> labsBySubject = getIncompleteLabNames(boardId);
//
//        for (Subject subject : subjects) {
//            String subjectName = subject.getName();
//            List<String> labNames = labsBySubject.getOrDefault(subjectName, List.of());
//            System.out.println(labsBySubject);
//            List<Lesson> lessons = subject.getLessons()
//                    .stream()
//                    .filter(lesson -> lesson.getLessonType() == LessonType.LABORATORY &&
//                            (lesson.getNumSubgroup() == 0 || lesson.getNumSubgroup() == subgroupNumber))
//                    .toList();
//
//            for (int i = 0; i < lessons.size(); i++) {
//                Lesson lesson = lessons.get(i);
//                String labName = (i < labNames.size()) ? labNames.get(i) : "ЛабX";
//
//                createLabCard(lesson, subjectName, labName, listId);
//            }
//        }
//    }
//
//    public Card createLabCard(Lesson lesson, String subjectName, String labName, String listId) {
//        String name = labName + " (" + subjectName + ")";
//        Date due = Date.from(lesson.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
//
//        Card card = new Card();
//        card.setName(name);
//        card.setDue(due);
//        return apiClient.createLessonCard(card, listId);
//    }
//
//    public TList getListById(String listId) throws IOException {
//        TList list = trelloApi.getList(listId);
//        updateTList(list);
//        return list;
//    }
//
//    public TList getListByName(String name, String boardId) throws IOException {
//        Board board = trelloApi.getBoard(boardId);
//        List<TList> lists = board.fetchLists();
//        TList tList = lists.stream()
//                .filter(list -> Objects.equals(list.getName(), name))
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("List with name " + name + " not found"));
//        updateTList(tList);
//        return tList;
//    }
//
//    public Map<String, List<String>> getIncompleteLabNames(String boardId) throws IOException {
//        String listName = "Количество лаб";
//        Map<String, List<String>> labsBySubject = new HashMap<>();
//
//        TList labCountList = getListByName(listName, boardId);
//        updateTList(labCountList);
//
//        for (Card card : labCountList.getCards()) {
//            processCard(card, labsBySubject);
//        }
//
//        return labsBySubject;
//    }
//
//    private void processCard(Card card, Map<String, List<String>> labsBySubject) throws IOException {
//        for (String checkListId : card.getIdChecklists()) {
//            CheckList checkList = getCheckListById(checkListId);
//            List<String> incompleteLabNames = getIncompleteLabNames(checkList);
//            labsBySubject.computeIfAbsent(subjectName, k -> new ArrayList<>()).addAll(incompleteLabNames);
//        }
//    }
//
//    private List<String> getIncompleteLabNames(CheckList checkList) {
//        return checkList.getCheckItems()
//                .stream()
//                .filter(item -> item.getState().equals("incomplete"))
//                .map(CheckItem::getName)
//                .toList();
//    }
//
//    private CheckList getCheckListById(String checkListId) throws IOException {
//        CheckListDto checkListDto = trelloApiClient.get
//                (
//                        "/checklists/" + checkListId,
//                        CheckListDto.class,
//                        Map.of("key", TRELLO_KEY, "token", TRELLO_TOKEN)
//                );
//
//        return checkListDto.createCheckList();
//    }
}
