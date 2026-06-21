package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class Lesson {
    String subjectName;
    private int numSubgroup;
    private LessonType lessonType;
    private LocalDate date;
    private LocalTime startLessonTime;
    private LocalTime endLessonTime;
    private String note;
}
