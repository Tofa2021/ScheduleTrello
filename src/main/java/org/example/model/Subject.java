package org.example.model;

import lombok.Getter;
import lombok.Setter;
import org.example.util.Pair;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return new Pair<>(null, null);
        }
        LocalDate earliestDate = lessons.stream().map(Lesson::getDate).min(Comparator.naturalOrder()).get();
        LocalDate latestDate = lessons.stream().map(Lesson::getDate).max(Comparator.naturalOrder()).get();
        return new Pair<>(earliestDate, latestDate);
    }

    private void updateBoundDates() {
        Pair<LocalDate, LocalDate> dates = findEarliestAndLatestDate(lessons);
        LocalDate newLessonsStartDate = dates.first();
        LocalDate newLessonsEndDate = dates.second();

        if (newLessonsStartDate == null) {
            newLessonsStartDate = LocalDate.of(2026, 3, 20);
        }

        if (newLessonsEndDate == null) {
            newLessonsEndDate = LocalDate.of(2026, 3, 20);
        }

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
        return getFilteredLessons(null, null, null, null, Set.of(lessonType));
    }

    public List<Lesson> getLessonsByTypes(Set<LessonType> types) {
        return getFilteredLessons(null, null, null, null, types);
    }

    public List<Lesson> getRemainingLessons(LocalDate dateFrom) {
        return getFilteredLessons(dateFrom, null, null, null, null);
    }

    public List<Lesson> getRemainingAndTypesLessons(LocalDate dateFrom, Set<LessonType> types) {
        return getFilteredLessons(dateFrom, null, null, null, types);
    }

    public List<Lesson> getNotOnlySubgroupLessons(int subgroupNumber) {
        return getFilteredLessons(null, null, subgroupNumber, false, null);
    }

    private List<Lesson> getFilteredLessons(
            LocalDate dateFrom,
            LocalDate dateTo,
            Integer subgroupNumber,
            Boolean isSubgroupOnly,
            Set<LessonType> lessonTypes
    ) {
        Stream<Lesson> stream = lessons.stream();

        if (dateFrom != null) {
            stream = stream.filter(lesson -> lesson.getDate().isAfter(dateFrom));
        }

        if (dateTo != null) {
            stream = stream.filter(lesson -> lesson.getDate().isBefore(dateTo));
        }

        if (subgroupNumber != null) {
            if (isSubgroupOnly == null) {
                throw new RuntimeException("ТЫ дурачок");
            }
            if (isSubgroupOnly) {
                stream = stream.filter(lesson -> lesson.getNumSubgroup() == subgroupNumber);
            } else {
                stream = stream.filter(lesson -> lesson.getNumSubgroup() == subgroupNumber || lesson.getNumSubgroup() == 0);
            }
        }

        if (lessonTypes != null) {
            stream = stream.filter(lesson -> lessonTypes.contains(lesson.getLessonType()));
        }

        return stream.toList();
    }

    public List<Lesson> getRemainingNotOnlySubgroupLessons(int subgroupNumber, LocalDate dateFrom) {
        return getFilteredLessons(dateFrom, null, subgroupNumber, false, null);
    }
}
