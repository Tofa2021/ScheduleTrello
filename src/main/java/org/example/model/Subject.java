package org.example.model;

import lombok.Data;
import org.example.LessonType;
import org.example.Nameable;

import javax.naming.Name;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Subject implements Nameable {
    private String name;
    private LocalDate startLessonDate;
    private LocalDate endLessonDate;
    private List<Lesson> lessons;

    public Subject(String name, LocalDate startLessonDate, LocalDate endLessonDate, List<Lesson> lessons) {
        this.name = name;
        this.startLessonDate = startLessonDate;
        this.endLessonDate = endLessonDate;
        this.lessons = new ArrayList<>(lessons);
    }

    public void addLessons(List<Lesson> lessons, LocalDate newLessonsStartDate,LocalDate newLessonsEndDate) {
        if (endLessonDate.isBefore(newLessonsEndDate)){
            endLessonDate = newLessonsEndDate;
        }
        if (startLessonDate.isAfter(newLessonsStartDate)) {
            startLessonDate = newLessonsStartDate;
        }
        this.lessons.addAll(lessons);
        this.lessons = this.lessons.stream().sorted(Comparator.comparing(Lesson::getDate)).collect(Collectors.toList());
    }

    public List<Lesson> getLessonsByType(LessonType lessonType) {
        return lessons.stream()
                .filter(lesson -> lesson.getLessonType() == lessonType)
                .toList();
    }
}
