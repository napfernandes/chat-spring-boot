package com.napfernandes.chat.gson;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class GsonByteArrayToBase64 implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return Base64.getDecoder().decode(json.getAsString());
    }

    public JsonElement serialize(byte[] source, Type typeOfSrc, JsonSerializationContext context) {
        try {
            String encodedSource = new String(Base64.getEncoder().encode(source), StandardCharsets.UTF_8);

            return new JsonPrimitive(encodedSource);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}