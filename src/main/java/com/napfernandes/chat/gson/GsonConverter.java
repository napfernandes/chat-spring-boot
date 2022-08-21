package com.napfernandes.chat.gson;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@Component
public class GsonConverter {
    protected final Gson gson;

    public GsonConverter() {
        final GsonBuilder builder = new GsonBuilder();

        builder.excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
                .registerTypeHierarchyAdapter(byte[].class, new GsonByteArrayToBase64())
                .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime());

        gson = builder.create();
    }

    public <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(String stringValue) {
        Type itemType = new TypeToken<T>() {
        }.getType();
        return this.fromJson(stringValue, itemType);
    }

    public <T> T fromJson(String stringValue, Type castingType) {
        return gson.fromJson(stringValue, castingType);
    }

    public <T> List<T> fromJsonList(String stringValue, Class<T> clazz) {
        Type typeFromClass = TypeToken.get(clazz).getType();
        Type listType = TypeToken.getParameterized(List.class, typeFromClass).getType();

        return gson.fromJson(stringValue, listType);
    }
}
