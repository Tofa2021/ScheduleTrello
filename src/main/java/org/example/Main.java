package org.example;

import org.example.model.Lesson;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        var a = ScheduleParser.getLessons("414302").stream().filter(lesson -> Objects.equals(lesson.getLessonTypeAbbrev(), "ЛР") && lesson.getNumSubgroup() != 2).collect(Collectors.groupingBy(Lesson::getSubject));
        for (Map.Entry<String, List<Lesson>> entry : a.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println();
            entry.getValue().forEach(System.out::println);
            System.out.println();
        }
    }
}