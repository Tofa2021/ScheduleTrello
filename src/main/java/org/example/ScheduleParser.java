package org.example;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.dto.GroupScheduleDto;
import org.example.dto.LessonDto;
import org.example.model.Lesson;
import org.example.model.Subject;

public class ScheduleParser {
    public static List<Subject> getSubjects(String studentGroup) throws IOException {
        Map<String, Subject> subjectMap = new HashMap<>();
        Map<String, List<LessonDto>> schedules = ScheduleService.getGroupSchedule(studentGroup).getSchedules();

        for (Map.Entry<String, List<LessonDto>> entry : schedules.entrySet()){
            for (LessonDto lessonDto : entry.getValue()) {
                processSubject(lessonDto, entry.getKey(), ScheduleService.getCurrentWeekNumber(), subjectMap);
            }
        }

        return new ArrayList<>(subjectMap.values());
    }

    private static void processSubject(
            LessonDto lessonDto,
            String rusDayOfWeek,
            int currentWeekNumber,
            Map<String, Subject> subjectMap
    ) throws IOException {
        String name = lessonDto.getSubject();
        LocalDate startLessonDate = Utils.convertToDate(lessonDto.getStartLessonDate());
        LocalDate endLessonDate = Utils.convertToDate(lessonDto.getEndLessonDate());
        List<Lesson> lessons = LessonConvertor.convertLessonDtoToLessons(lessonDto, Utils.convertToEngDayOfWeek(rusDayOfWeek), currentWeekNumber)
                .stream()
                .distinct()
                .collect(Collectors.toList());
        subjectMap.merge(name, new Subject(name, startLessonDate, endLessonDate, lessons), ScheduleParser::mergeSubjects);
    }

    private static Subject mergeSubjects(Subject existing, Subject newSubject) {
        existing.addLessons(newSubject.getLessons(), newSubject.getStartLessonDate(), newSubject.getEndLessonDate());
        return existing;
    }
}
