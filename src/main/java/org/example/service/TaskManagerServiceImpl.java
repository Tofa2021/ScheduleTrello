package org.example.service;

import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.CheckList;
import com.julienvey.trello.domain.TList;
import lombok.RequiredArgsConstructor;
import org.example.model.Lesson;
import org.example.model.LessonType;
import org.example.model.Subject;
import org.example.presentation.api.task_manager.TaskManagerApiClient;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class TaskManagerServiceImpl implements TaskManagerService {
    private final TaskManagerApiClient taskManagerApiClient;

    @Override
    public void createTask(Lesson lesson, String name, String listId) {
        Date due = Date.from(lesson.getDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        Card card = new Card();
        card.setName(name);
        card.setDue(due);

        taskManagerApiClient.createTask(card, listId);
    }

    private String getCardName(LessonType type, String number, String subjectName) {
        String typeString = switch (type) {
            case PRACTICAL -> "ПЗ";
            case LABORATORY -> "Лаб";
            case LECTURE -> "Лекция";
        };
        return typeString + " " + number + " " + "(" + subjectName + ")";
    }

    @Override
    public void createSubjectTasks(Subject subject, int labCount, String listId) {
        int i = 1;
        String number;
        for (Lesson lesson : subject.getLessonsByTypes(Set.of(LessonType.LABORATORY, LessonType.PRACTICAL))) {
            if (labCount >= i) {
                number = String.valueOf(i);
            } else {
                number = "X";
            }

            createTask(lesson, getCardName(lesson.getLessonType(), number, subject.getName()), listId);
            i++;
        }
    }

    @Override
    public Map<String, Integer> getSubjectLabCount(String boardId, String listName) {
        Map<String, Integer> labCounts = new HashMap<>();
        TList labCountList = taskManagerApiClient.getList(listName, boardId);
        for (Card card : labCountList.getCards()) {
            String checkListId = card.getIdChecklists().getFirst();
            CheckList checkList = taskManagerApiClient.getCheckList(checkListId);

            int labCount = checkList.getCheckItems()
                    .stream()
                    .filter(item -> item.getState().equals("incomplete"))
                    .toList()
                    .size();

            labCounts.put(card.getName(), labCount);
        }
        return labCounts;
    }
}
