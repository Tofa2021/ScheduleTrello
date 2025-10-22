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
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final Gson GSON = new Gson();
    private static final String BASE_URL = "https://iis.bsuir.by/api/v1";

    private static String executeRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("Request failed with code: " + response.code());
            }
        }
    }

    public static GroupScheduleDto getGroupSchedule(String studentGroupId) throws IOException {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/schedule")
                .newBuilder()
                .addQueryParameter("studentGroup", studentGroupId)
                .build();

        String json = executeRequest(url.toString());
        return GSON.fromJson(json, GroupScheduleDto.class);
    }

    public static int getCurrentWeekNumber() throws IOException {
        String url = BASE_URL + "/schedule/current-week";
        String json = executeRequest(url);
        return GSON.fromJson(json, Integer.class);
    }

    public static List<Subject> getSubjects(String studentGroup) throws IOException {
        List<Subject> subjects = new ArrayList<>();
        for (Map.Entry<String, List<LessonDto>> entry : getGroupSchedule(studentGroup).getSchedules().entrySet()){
            outerLoop:
            for (LessonDto lessonDto : entry.getValue()) {
                String name = lessonDto.getSubject();
                LocalDate startLessonDate = DateConverter.convertToDate(lessonDto.getStartLessonDate());
                LocalDate endLessonDate = DateConverter.convertToDate(lessonDto.getEndLessonDate());
                List<Lesson> lessons = convertLessonDtoToLessons(lessonDto, convertToEngDayOfWeek(entry.getKey()))
                        .stream()
                        .distinct()
                        .collect(Collectors.toList());
                for (Subject subject : subjects) {
                    if (Objects.equals(subject.getName(), name)) {
                        subject.addLessons(lessons, startLessonDate, endLessonDate);
                        continue outerLoop;
                    }
                }
                subjects.add(new Subject(name, startLessonDate, endLessonDate, lessons));
            }
        }

        return subjects;
    }

    public static List<Lesson> convertLessonDtoToLessons(LessonDto lessonDto, DayOfWeek lessonDayOfWeek) throws IOException {
        List<Lesson> lessons = new ArrayList<>();
        for (LocalDate date : calculateDate(lessonDto, lessonDayOfWeek, getCurrentWeekNumber())) {
            Lesson lesson = new Lesson(lessonDto.getNumSubgroup(), LessonType.convertToLessonType(lessonDto.getLessonTypeAbbrev()), date);
            lessons.add(lesson);
        }

        return lessons;
    }

    public static List<LocalDate> calculateDate(LessonDto lessonDto, DayOfWeek lessonDtoDayOfWeek, int currentWeekNumber) {
        LocalDate endLessonDate = DateConverter.convertToDate(lessonDto.getEndLessonDate());
        List<LocalDate> dates = new ArrayList<>();
        for (int weekNumber : lessonDto.getWeekNumber()) {
            LocalDate date = findFirstDate(LocalDate.now().getDayOfWeek(), lessonDtoDayOfWeek, currentWeekNumber, weekNumber, LocalDate.now());
            while (date.isBefore(endLessonDate) || date.isEqual(endLessonDate)) { // maybe ran out of bounds
                dates.add(date);
                date = date.plusWeeks(4);
            }
        }

        return dates;
    }

    public static LocalDate findFirstDate
            (
                DayOfWeek currentDayOfWeek,
                DayOfWeek lessonDayOfWeek,
                int currentWeekNumber,
                int lessonWeekNumber,
                LocalDate currentDate
            )
    {
        if (currentWeekNumber == lessonWeekNumber) {
            if (currentDayOfWeek == lessonDayOfWeek) {
                return currentDate;
            }

            int dayOffset = lessonDayOfWeek.getValue() - currentDayOfWeek.getValue(); //
            return currentDate.plusDays(dayOffset);
        }

        int weekOffset = calculateWeekOffset(lessonWeekNumber, currentWeekNumber);
        int totalDaysOffset = weekOffset * 7 + (lessonDayOfWeek.getValue() - currentDayOfWeek.getValue());

        return currentDate.plusDays(totalDaysOffset);
    }

    private static int calculateWeekOffset(int lessonWeekNumber, int currentWeekNumber) {
        int offset = lessonWeekNumber - currentWeekNumber;
        if (offset < 0) offset += 4;
        return offset;
    }

    public static DayOfWeek convertToEngDayOfWeek(String rusDayOfWeek) {
        int dayOfWeek = 0;
        dayOfWeek = switch (rusDayOfWeek) {
            case "Понедельник" -> 1;
            case "Вторник" -> 2;
            case "Среда" -> 3;
            case "Четверг" -> 4;
            case "Пятница" -> 5;
            case "Суббота" -> 6;
            case "Воскресенье" -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + rusDayOfWeek);
        };

        return DayOfWeek.of(dayOfWeek);
    }
}
