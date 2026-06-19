package org.example.service;

import org.example.dto.LessonDto;

import java.time.LocalDate;
import java.util.List;

public interface DateCalculator {
    List<LocalDate> calculateDates(LessonDto lessonDto, LocalDate currentDate, int currentWeekNumber);
}
