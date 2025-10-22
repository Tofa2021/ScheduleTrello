package org.example;

import org.example.dto.LessonDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateCalculator {
    public static List<LocalDate> calculateDates(LessonDto lessonDto, DayOfWeek lessonDtoDayOfWeek, int currentWeekNumber) {
        LocalDate endLessonDate = Utils.convertToDate(lessonDto.getEndLessonDate());
        List<LocalDate> dates = new ArrayList<>();
        for (int weekNumber : lessonDto.getWeekNumber()) {
            LocalDate date = findFirstDate(LocalDate.now().getDayOfWeek(), lessonDtoDayOfWeek, currentWeekNumber, weekNumber, LocalDate.now());
            while (date.isBefore(endLessonDate) || date.isEqual(endLessonDate)) { // maybe ran out of bounds
                dates.add(date);
                date = date.plusWeeks(4);
            }
        }

        return dates;
    }

    public static LocalDate findFirstDate
            (
                    DayOfWeek currentDayOfWeek,
                    DayOfWeek lessonDayOfWeek,
                    int currentWeekNumber,
                    int lessonWeekNumber,
                    LocalDate currentDate
            )
    {
        if (currentWeekNumber == lessonWeekNumber) {
            if (currentDayOfWeek == lessonDayOfWeek) {
                return currentDate;
            }

            int dayOffset = lessonDayOfWeek.getValue() - currentDayOfWeek.getValue(); //
            return currentDate.plusDays(dayOffset);
        }

        int weekOffset = calculateWeekOffset(lessonWeekNumber, currentWeekNumber);
        int totalDaysOffset = weekOffset * 7 + (lessonDayOfWeek.getValue() - currentDayOfWeek.getValue());

        return currentDate.plusDays(totalDaysOffset);
    }

    private static int calculateWeekOffset(int lessonWeekNumber, int currentWeekNumber) {
        int offset = lessonWeekNumber - currentWeekNumber;
        if (offset < 0) offset += 4;
        return offset;
    }
}
