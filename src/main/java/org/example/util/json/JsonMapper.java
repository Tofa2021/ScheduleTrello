package org.example.util.json;

import java.lang.reflect.Type;

public interface JsonMapper {
    <T> String toJson(T data);

    <T> T fromJson(String json, Type type);
}
