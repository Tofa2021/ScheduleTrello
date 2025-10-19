package org.example;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.dto.GroupScheduleDto;
import org.example.dto.LessonDto;

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

    public static List<LessonDto> getCurrentWeekLessons() throws IOException{
        int weekNumber = getCurrentWeekNumber();
        GroupScheduleDto groupSchedule = getGroupSchedule("414302");
        return groupSchedule.getLessons().values().stream()
                .flatMap(List::stream)
                .filter(lessonDto -> lessonDto.getWeekNumber().contains(weekNumber))
                .toList();
    }

    public static List<LessonDto> getCurrentMonthLessons() throws IOException {
        int weekNumber = getCurrentWeekNumber();
        GroupScheduleDto groupSchedule = getGroupSchedule("414302");
    }
}
