package org.example;

import org.example.model.Subject;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String studentGroup = "414302";

        Subject OOPiP = ScheduleParser.getSubjects(studentGroup).stream().filter(subject -> subject.getName().equals("БАУИ")).findFirst().orElse(null);
        OOPiP.getLessons().stream().filter(lesson -> lesson.getNumSubgroup() != 2 && lesson.getLessonType() == LessonType.LABORATORY).forEach(System.out::println);
    }
}
