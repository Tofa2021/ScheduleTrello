package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Lesson;
import org.example.model.Subject;
import org.example.presentation.api.schedule.ScheduleApiClient;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleApiClient scheduleApiClient;
    private final SubjectConverter subjectConverter;
    private String groupId;
    private List<Subject> subjects;

    @Override
    public Subject getSubject(String name, String groupId) {
        if (!Objects.equals(this.groupId, groupId)) {
            refreshSubjects(groupId);
        }

        return subjects.stream()
                .filter(subject -> subject.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Subject not found with name = " + name));
    }

    @Override
    public List<Subject> getSubjects(String groupId) {
        if (!Objects.equals(this.groupId, groupId)) {
            refreshSubjects(groupId);
        }

        return subjects;
    }

    @Override
    public List<String> getSubjectNames(String groupId) {
        if (!Objects.equals(this.groupId, groupId)) {
            refreshSubjects(groupId);
        }

        return subjects.stream()
                .map(Subject::getName)
                .toList();
    }

    @Override
    public List<Lesson> getDateLessons(LocalDate date, String groupId) {
        return getDatePeriodLessons(date, date, groupId);
    }

    @Override
    public List<Lesson> getDatePeriodLessons(LocalDate dateFrom, LocalDate dateTo, String groupId) {
        if (subjects == null) {
            refreshSubjects(groupId);
        }

        return subjects.stream()
                .map(subject -> subject.getDatePeriodLessons(dateFrom, dateTo))
                .flatMap(List::stream)
                .sorted(Comparator.comparing(Lesson::getStartLessonTime))
                .collect(Collectors.toList());
    }

    private void refreshSubjects(String groupId) {
        subjects = subjectConverter.convertSubjects(
                scheduleApiClient.getGroupSchedule(groupId),
                scheduleApiClient.getCurrentWeekNumber()
        );
        this.groupId = groupId;
    }
}
