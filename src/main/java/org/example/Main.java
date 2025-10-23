    package org.example;

    import com.julienvey.trello.domain.Board;
    import com.julienvey.trello.domain.TList;
    import org.example.model.Lesson;
    import org.example.model.Subject;

    import java.util.List;
    import java.util.Map;

    public class Main {
        public static void main(String[] args) throws Exception {
            String studentGroup = "414302";
            int subgroupNumber = 1;
            String boardId = "68e6984ec50df586088d6dff";
            String listId = "680ba9eeea5071c6bd6503b6";

            TrelloService trelloService = new TrelloService();
            trelloService.createLabCards(ScheduleParser.getSubjects(studentGroup), subgroupNumber, boardId, listId);
        }
    }
