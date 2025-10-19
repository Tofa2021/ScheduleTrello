package org.example;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScheduleParser {
    public static void getGroupSchedule(){
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = HttpUrl.parse("https://iis.bsuir.by/api/v1/schedule")
                .newBuilder()
                .addQueryParameter("studentGroup", "414302")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String jsonString = response.body().string();
            } else {
                System.out.println("Request failed: " + response.code());
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
