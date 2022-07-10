package com.napfernandes.chat.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class WebSocketMessageEncoder implements Encoder.Text<WebSocketMessage> {
    private static Gson gson = new Gson();

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public String encode(WebSocketMessage message) throws EncodeException {
        return gson.toJson(message);
    }
}
