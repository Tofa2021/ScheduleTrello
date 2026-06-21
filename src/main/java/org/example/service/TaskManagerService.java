package org.example.service;

import org.example.model.Lesson;

import java.util.List;
import java.util.Map;

public interface TaskManagerService {
    void createTask(Lesson lesson, String name, String listId);

    void createTasks(List<Lesson> lessons, String subjectName, int labCount, String listId);

    void createAutoLabCountTasks(Map<String, List<Lesson>> lessonsBySubjectName, String listToCreateId, String labCountListId);

    Map<String, Integer> getSubjectLabCount(String boardId, String listName);

    Map<String, Integer> getSubjectLabCount(String listId);
}
