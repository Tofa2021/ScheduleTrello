package org.example.model;

import lombok.Data;
import org.example.dto.EmployeeDto;
import org.example.dto.LessonDto;
import org.example.dto.SimpleStudentGroupDto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Data
public class Lesson {
    private List<Integer> weekNumber;
    private int numSubgroup; //Если 0 - значит подгруппы у пары нету
    private String startLessonTime;
    private String endLessonTime;
    private String subject;
    private String lessonTypeAbbrev;
    private LocalDate startLessonDate;
    private LocalDate endLessonDate;
    private List<LocalDate> date;
    private DayOfWeek dayOfWeek;

    public Lesson (LessonDto lessonDto) {
        weekNumber = lessonDto.getWeekNumber();
        numSubgroup = lessonDto.getNumSubgroup();
        startLessonTime = lessonDto.getStartLessonTime();
        endLessonTime = lessonDto.getEndLessonTime();
        subject = lessonDto.getSubject();
        lessonTypeAbbrev = lessonDto.getLessonTypeAbbrev();
        startLessonDate = convertToDate(lessonDto.getStartLessonDate());
        endLessonDate = convertToDate(lessonDto.getEndLessonDate());
    }

    private LocalDate convertToDate(String stringDate) {
        String[] parts = stringDate.split("\\.");
        List<Integer> dateParts = Arrays.stream(parts).map(Integer::parseInt).toList();
        return LocalDate.of(dateParts.get(2), dateParts.get(1), dateParts.get(0));
    }

    @Override
    public String toString() {
        return "Lesson " +
                subject + '\'' +
                ", lessonTypeAbbrev='" + lessonTypeAbbrev + '\'' +
                ", date=" + date;
    }
}
