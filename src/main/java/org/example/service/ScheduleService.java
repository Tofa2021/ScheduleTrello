package org.example.service;

import org.example.model.Subject;

import java.util.List;

public interface ScheduleService {
    Subject getSubject(String name, String groupId);

    List<String> getSubjectNames(String groupId);
}
