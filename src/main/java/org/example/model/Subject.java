package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.util.Pair;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class Subject implements Nameable {
    private String name;
    private LocalDate startLessonDate;
    private LocalDate endLessonDate;
    private List<Lesson> lessons;

    public Subject(String name, List<Lesson> lessons) {
        this.name = name;
        setLessons(lessons);
    }

    private Pair<LocalDate, LocalDate> findEarliestAndLatestDate(List<Lesson> lessons) {
        if (lessons.isEmpty()) {
            throw new RuntimeException("Lessons is empty");
        }
        LocalDate earliestDate = lessons.stream().map(Lesson::getDate).min(Comparator.naturalOrder()).get();
        LocalDate latestDate = lessons.stream().map(Lesson::getDate).max(Comparator.naturalOrder()).get();
        return new Pair<>(earliestDate, latestDate);
    }

    private void updateBoundDates() {
        Pair<LocalDate, LocalDate> dates = findEarliestAndLatestDate(lessons);
        LocalDate newLessonsStartDate = dates.first();
        LocalDate newLessonsEndDate = dates.second();

        if (endLessonDate == null) {
            endLessonDate = newLessonsEndDate;
        }

        if (startLessonDate == null) {
            startLessonDate = newLessonsStartDate;
        }

        if (endLessonDate.isBefore(newLessonsEndDate)) {
            endLessonDate = newLessonsEndDate;
        }

        if (startLessonDate.isAfter(newLessonsStartDate)) {
            startLessonDate = newLessonsStartDate;
        }
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
        updateBoundDates();
    }

    public void addLessons(List<Lesson> lessons) {
        lessons.addAll(this.lessons);
        setLessons(lessons.stream().distinct().collect(Collectors.toList()));
    }

    public List<Lesson> getLessonsByType(LessonType lessonType) {
        return lessons.stream()
                .filter(lesson -> lesson.getLessonType() == lessonType)
                .toList();
    }

    public List<Lesson> getLessonsByTypes(Set<LessonType> types) {
        return lessons.stream()
                .filter(lesson -> types.contains(lesson.getLessonType()))
                .toList();
    }
}
