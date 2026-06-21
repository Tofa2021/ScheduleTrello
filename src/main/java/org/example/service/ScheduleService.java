package org.example.service;

import org.example.model.Lesson;
import org.example.model.Subject;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    Subject getSubject(String name, String groupId);

    List<Subject> getSubjects(String groupId);

    List<String> getSubjectNames(String groupId);

    List<Lesson> getDateLessons(LocalDate date, String groupId);

    List<Lesson> getDatePeriodLessons(LocalDate dateFrom, LocalDate dateTo, String groupId);
}
