package org.example;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.dto.GroupScheduleDto;
import org.example.dto.LessonDto;
import org.example.model.Lesson;

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

    public static List<Lesson> getLessons(String studentGroup) throws IOException {
        List<Lesson> lessons = new ArrayList<>();

        for (Map.Entry<String, List<LessonDto>> entry : getGroupSchedule(studentGroup).getSchedules().entrySet()){
            for (LessonDto lessonDto : entry.getValue()) {
                Lesson lesson = new Lesson(lessonDto);
                lesson.setDayOfWeek(convertToEngDayOfWeek(entry.getKey()));
                lesson.setDates(calculateDate(lesson, getCurrentWeekNumber()).stream().sorted().toList());
                lessons.add(lesson);
            }
        }

        return lessons;
    }

    public static List<LocalDate> calculateDate(Lesson lesson, int currentWeekNumber) {
        List<LocalDate> dates = new ArrayList<>();
        for (int weekNumber : lesson.getWeekNumber()) {
            LocalDate date = findFirstDate(LocalDate.now().getDayOfWeek(), lesson.getDayOfWeek(), currentWeekNumber, weekNumber, LocalDate.now());
            while (date.isBefore(lesson.getEndLessonDate())) {
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
