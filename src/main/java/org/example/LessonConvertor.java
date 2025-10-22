package org.example;

import org.example.dto.LessonDto;
import org.example.model.Lesson;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LessonConvertor {
    public static List<Lesson> convertLessonDtoToLessons(LessonDto lessonDto, DayOfWeek lessonDtoDayOfWeek, int currentWeekNumber) throws IOException {
        List<Lesson> lessons = new ArrayList<>();
        for (LocalDate date : DateCalculator.calculateDates(lessonDto, lessonDtoDayOfWeek, currentWeekNumber)) {
            lessons.add(new Lesson(lessonDto.getNumSubgroup(), LessonType.convertToLessonType(lessonDto.getLessonTypeAbbrev()), date));
        }

        return lessons;
    }
}
