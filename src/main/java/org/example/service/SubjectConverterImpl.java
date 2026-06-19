package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LessonDto;
import org.example.model.Lesson;
import org.example.model.Subject;
import org.example.util.Utils;

import java.util.List;

@RequiredArgsConstructor
public class SubjectConverterImpl implements SubjectConverter {
    private final LessonConverter lessonConverter;

    @Override
    public Subject convertSubject(LessonDto lessonDto, String rusDayOfWeek, int currentWeekNumber) {
        List<Lesson> lessons = lessonConverter.convert(lessonDto, Utils.convertToEngDayOfWeek(rusDayOfWeek), currentWeekNumber);
        return new Subject(lessonDto.getSubject(), lessons);
    }
}
