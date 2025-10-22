package org.example.model;

import lombok.Data;
import org.example.LessonType;

import java.time.LocalDate;

@Data
public class Lesson {
    private int numSubgroup;
    private LessonType lessonType;
    private LocalDate date;

    public Lesson(int numSubgroup, LessonType lessonType, LocalDate date) {
        this.numSubgroup = numSubgroup;
        this.lessonType = lessonType;
        this.date = date;
    }
}
