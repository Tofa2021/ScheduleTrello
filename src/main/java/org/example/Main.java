    package org.example;

    import org.example.model.Lesson;

    import java.io.IOException;
    import java.time.LocalDate;

    public class Main {
        public static void main(String[] args) throws IOException {
//            Board board = trelloApi.getBoard("68e6984ec50df586088d6dff");
//            TList inboxList = trelloApi.getList("680ba9eeea5071c6bd6503b6");

            TrelloService trelloService = new TrelloService();
            trelloService.createLabCard(new Lesson(1, LessonType.LABORATORY, LocalDate.now()), "FFF", 3, "680ba9eeea5071c6bd6503b6");
        }
    }
