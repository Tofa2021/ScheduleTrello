package org.example.service;

import org.example.dto.LessonDto;
import org.example.model.Lesson;

import java.time.DayOfWeek;
import java.util.List;

public interface LessonConverter {
    List<Lesson> convert(LessonDto lessonDto, DayOfWeek dtoDayOfWeek, int currentWeekNumber);
}
