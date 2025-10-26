    package org.example;

    import com.google.gson.internal.bind.util.ISO8601Utils;
    import com.julienvey.trello.domain.Board;
    import com.julienvey.trello.domain.TList;
    import org.example.model.Lesson;
    import org.example.model.Subject;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Map;
    import java.util.Scanner;

    public class Main {
        public static void main(String[] args) throws Exception {
            String studentGroup = "414302";
            int subgroupNumber = 1;
            String boardId = "68e6984ec50df586088d6dff";
            String listId = "680ba9eeea5071c6bd6503b6";

            System.out.println("Выберите действие:");
            System.out.println("1) Создать карточки по расписанию");
            System.out.println("2) Просмотр пар по предмету");
            switch (Utils.scanInt()) {
                case 1:
                    TrelloService trelloService = new TrelloService();
                    trelloService.createLabCards(ScheduleParser.getSubjects(studentGroup), subgroupNumber, boardId, listId);
                    break;
                case 2:
                    List<Subject> subjects = ScheduleParser.getSubjects(studentGroup);
                    Subject subject = Utils.select(subjects.toArray(new Subject[0]));
                    LessonType lessonType = Utils.select(LessonType.values());
                    subject.getLessonsByType(lessonType).stream().filter(lesson -> lesson.getNumSubgroup() != 2).forEach(System.out::println);
                default:
                    System.out.println("Нет такого пункта");
            }

        }
    }
