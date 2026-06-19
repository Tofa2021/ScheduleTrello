package org.example.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Lesson {
    private int numSubgroup;
    private LessonType lessonType;
    private LocalDate date;
    private String note;

    public Lesson(int numSubgroup, LessonType lessonType, LocalDate date, String note) {
        this.numSubgroup = numSubgroup;
        this.lessonType = lessonType;
        this.date = date;
        this.note = note;
    }
}
