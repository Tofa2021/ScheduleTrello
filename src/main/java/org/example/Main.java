package org.example;

import org.example.model.Lesson;
import org.example.model.Subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        var a = ScheduleParser.getLessons("414302").stream()
                .filter(lesson -> Objects.equals(lesson.getLessonTypeAbbrev(), "ЛК")
                        && lesson.getNumSubgroup() != 2)
                .collect(Collectors.groupingBy(Lesson::getName));
        List<Subject> subjects = new ArrayList<>();
        for (Map.Entry<String, List<Lesson>> entry : a.entrySet()) {
            subjects.add(new Subject(entry.getValue()));
        }

        subjects.forEach(System.out::println);
    }
}
