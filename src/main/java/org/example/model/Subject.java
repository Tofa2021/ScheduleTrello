package org.example.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Subject {
    private String name;
    private LocalDate startLessonDate;
    private LocalDate endLessonDate;
    private List<LocalDate> dates;

    public Subject(List<Lesson> lessons) {
        Lesson firstLesson = lessons.getFirst();
        name = firstLesson.getName();
        startLessonDate = firstLesson.getStartLessonDate();
        endLessonDate = firstLesson.getEndLessonDate();
        dates = lessons.stream()
                .flatMap(lesson -> lesson.getDates().stream())
                .distinct()
                .toList();
    }
}
