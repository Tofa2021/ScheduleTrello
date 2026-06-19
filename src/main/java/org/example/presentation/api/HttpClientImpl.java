package org.example.presentation.api;

import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.util.json.JsonMapper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
public class HttpClientImpl implements HttpClient {
    private final JsonMapper jsonMapper;
    private final OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    public <T> T get(String url, Type responseType, Map<String, String> params) {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder();

        for (Map.Entry<String, String> param : params.entrySet()) {
            urlBuilder.addQueryParameter(param.getKey(), param.getValue());
        }

        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("Accept", "application/json")
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed with code: " + response.code());
            }

            String json = response.body().string();
            return jsonMapper.fromJson(json, responseType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
