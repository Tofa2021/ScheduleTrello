package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.LessonDto;
import org.example.model.Lesson;
import org.example.model.LessonType;
import org.example.util.Utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class LessonConvertorImpl implements LessonConverter {
    private final DateCalculator dateCalculator;

    @Override
    public List<Lesson> convert(LessonDto lessonDto, DayOfWeek lessonDtoDayOfWeek, int currentWeekNumber) {
        List<Lesson> lessons = new ArrayList<>();

        for (LocalDate date : dateCalculator.calculateDates(lessonDto, LocalDate.now(), currentWeekNumber)) {
            lessons.add(new Lesson(
                    lessonDto.getSubject(),
                    lessonDto.getNumSubgroup(),
                    LessonType.convertToLessonType(lessonDto.getLessonTypeAbbrev()),
                    date,
                    Utils.convertToTime(lessonDto.getStartLessonTime()),
                    Utils.convertToTime(lessonDto.getEndLessonTime()),
                    lessonDto.getNote()));
        }

        return lessons;
    }
}
