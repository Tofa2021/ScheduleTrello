package org.example.presentation.api;

import java.lang.reflect.Type;
import java.util.Map;

public interface HttpClient {
    <T> T get(String url, Type responseType, Map<String, String> params);

    default <T> T get(String url, Type responseType) {
        return get(url, responseType, Map.of());
    }
}
