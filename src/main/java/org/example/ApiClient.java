package org.example;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.dto.GroupScheduleDto;

import java.io.IOException;

public class ApiClient {
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
}
