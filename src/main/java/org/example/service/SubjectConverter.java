package org.example.service;

import org.example.dto.GroupScheduleDto;
import org.example.dto.LessonDto;
import org.example.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SubjectConverter {
    Subject convertSubject(LessonDto lessonDto, String rusDayOfWeek, int currentWeekNumber);

    default List<Subject> convertSubjects(GroupScheduleDto groupSchedule, int currentWeekNumber) {
        Map<String, Subject> subjectMap = new HashMap<>();
        Map<String, List<LessonDto>> schedules = groupSchedule.getSchedules();
        for (Map.Entry<String, List<LessonDto>> entry : schedules.entrySet()) {
            for (LessonDto lessonDto : entry.getValue()) {
                Subject subject = convertSubject(lessonDto, entry.getKey(), currentWeekNumber);
                subjectMap.merge(subject.getName(), subject, (oldSubject, newSubject) -> {
                    oldSubject.addLessons(newSubject.getLessons());
                    return oldSubject;
                });
            }
        }
        return new ArrayList<>(subjectMap.values());
    }
}
