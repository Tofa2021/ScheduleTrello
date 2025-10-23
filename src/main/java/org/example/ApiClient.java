package org.example;

import com.google.gson.Gson;
import com.julienvey.trello.domain.Badges;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class ApiClient {
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final Gson GSON = new Gson();
    private final String BASE_URL;

    public ApiClient(String baseUrl) {
        BASE_URL = baseUrl;
    }

    public <T> T get(String endpoint, Type responseType, Map<String, String> params) throws IOException {
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
            return GSON.fromJson(json, responseType);
        }
    }
}
