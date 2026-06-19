package org.example.service;

import org.example.dto.LessonDto;
import org.example.util.Utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DateCalculatorImpl implements DateCalculator {
    @Override
    public List<LocalDate> calculateDates(LessonDto lessonDto, LocalDate currentDate, int currentWeekNumber) {
        if (lessonDto.getStartLessonDate() == null) {
            return new ArrayList<>();
        }
        LocalDate startLessonDate = Utils.convertToDate(lessonDto.getStartLessonDate());
        LocalDate endLessonDate = Utils.convertToDate(lessonDto.getEndLessonDate());

        List<LocalDate> dates = new ArrayList<>();
        for (int weekNumber : lessonDto.getWeekNumber()) {
            LocalDate date = findFirstDate(startLessonDate, currentDate, weekNumber, currentWeekNumber);
            while (date.isBefore(endLessonDate) || date.isEqual(endLessonDate)) {
                dates.add(date);
                date = date.plusWeeks(4);
            }
        }

        return dates.stream().distinct().sorted().collect(Collectors.toList());
    }

    private LocalDate findFirstDate(LocalDate startLessonDate, LocalDate currentDate, int weekNumber, int currentWeekNumber) {
        int startLessonWeekNumber = findWeekNumber(startLessonDate, currentWeekNumber, currentDate);
        if (startLessonWeekNumber == weekNumber) {
            return startLessonDate;
        } else {
            int difference = weekNumber - startLessonWeekNumber;
            if (difference < 0) {
                difference = 4 + difference;
            }
            return startLessonDate.plusWeeks(difference);
        }
    }

    private int findWeekNumber(LocalDate targetDate, int currentWeekNumber, LocalDate currentDate) {
        long daysDifference = ChronoUnit.DAYS.between(currentDate, targetDate);
        long weeksDifference = daysDifference / 7;
        int targetWeekNumber = currentWeekNumber + (int) (weeksDifference % 4);
        targetWeekNumber = ((targetWeekNumber - 1) % 4 + 4) % 4 + 1;
        return targetWeekNumber;
    }
}
