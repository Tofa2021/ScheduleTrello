package org.example.service;

import org.example.model.Lesson;
import org.example.model.Subject;

import java.util.List;
import java.util.Map;

public interface TaskManagerService {
    void createTask(Lesson lesson, String name, String listId);

    void createSubjectTasks(Subject subject, int labCount, String listId);

    default void createSubjectTasks(List<Subject> subjects, int labCount, String listId) {
        subjects.forEach(subject -> createSubjectTasks(subject, labCount, listId));
    }

    Map<String, Integer> getSubjectLabCount(String boardId, String listName);
}
