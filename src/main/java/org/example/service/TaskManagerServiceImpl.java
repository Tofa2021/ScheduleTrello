package org.example.service;

import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.CheckList;
import com.julienvey.trello.domain.TList;
import lombok.RequiredArgsConstructor;
import org.example.model.Lesson;
import org.example.model.LessonType;
import org.example.presentation.api.task_manager.TaskManagerApiClient;

import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public void createTasks(List<Lesson> lessons, String subjectName, int labCount, String listId) {
        int i = 1;
        String number;
        for (Lesson lesson : lessons) {
            if (labCount >= i) {
                number = String.valueOf(i);
            } else {
                number = "X";
            }

            createTask(lesson, getCardName(lesson.getLessonType(), number, subjectName), listId);
            i++;
        }
    }

    @Override
    public void createAutoLabCountTasks(
            Map<String, List<Lesson>> lessonsBySubjectName,
            String listToCreateTasksId,
            String labCountListId
    ) {
        Map<String, Integer> subjectLabCount = getSubjectLabCount(labCountListId);
        for (Map.Entry<String, List<Lesson>> entry : lessonsBySubjectName.entrySet()) {
            String subjectName = entry.getKey();
            List<Lesson> lessons = entry.getValue();
            int labCount = subjectLabCount.get(subjectName);

            createTasks(lessons, subjectName, labCount, listToCreateTasksId);
        }
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
    public Map<String, Integer> getSubjectLabCount(String boardId, String labCountListName) {
        Map<String, Integer> labCounts = new HashMap<>();
        TList labCountList = taskManagerApiClient.getList(labCountListName, boardId);
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

    @Override
    public Map<String, Integer> getSubjectLabCount(String listId) {
        Map<String, Integer> labCounts = new HashMap<>();
        TList labCountList = taskManagerApiClient.getList(listId);
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
