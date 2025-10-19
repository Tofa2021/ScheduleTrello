package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        for (var lesson : ScheduleParser.getCurrentWeekLessons()){
            System.out.println(
                    lesson.getSubject() + " " +
                    lesson.getLessonTypeAbbrev() + " " +
                    lesson.getNumSubgroup()
            );
        }
    }
}