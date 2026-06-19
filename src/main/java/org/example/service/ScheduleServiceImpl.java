package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.Subject;
import org.example.presentation.api.schedule.ScheduleApiClient;

import java.util.List;
import java.util.Objects;

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
    public List<String> getSubjectNames(String groupId) {
        if (!Objects.equals(this.groupId, groupId)) {
            refreshSubjects(groupId);
        }

        return subjects.stream()
                .map(Subject::getName)
                .toList();
    }

    private void refreshSubjects(String groupId) {
        subjects = subjectConverter.convertSubjects(
                scheduleApiClient.getGroupSchedule(groupId),
                scheduleApiClient.getCurrentWeekNumber()
        );
        this.groupId = groupId;
    }
}
