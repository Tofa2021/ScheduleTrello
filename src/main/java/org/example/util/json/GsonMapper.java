package org.example.util.json;

import java.lang.reflect.Type;

public class GsonMapper implements JsonMapper {
    private final com.google.gson.Gson GSON = new com.google.gson.Gson();
    
    @Override
    public <T> String toJson(T data) {
        return GSON.toJson(data);
    }

    @Override
    public <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
