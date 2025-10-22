package org.example;

import com.google.gson.Gson;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;

public class ApiClient {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final Gson GSON = new Gson();
    private static final String BASE_URL = "https://iis.bsuir.by/api/v1";

    public static  <T> T get(String endpoint, Class<T> responseClass, Map<String, String> params) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL + endpoint).newBuilder();

        if (params != null){
            params.forEach(urlBuilder::addQueryParameter);
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = CLIENT.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed with code: " + response.code());
            }

            String json = response.body().string();
            return GSON.fromJson(json, responseClass);
        }
    }
}
