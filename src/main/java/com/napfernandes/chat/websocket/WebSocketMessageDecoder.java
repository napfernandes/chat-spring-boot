package com.napfernandes.chat.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

public class WebSocketMessageDecoder implements Decoder.Text<WebSocketMessage> {

    private static Gson gson = new Gson();

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public WebSocketMessage decode(String stringMessage) throws DecodeException {
        return gson.fromJson(stringMessage, WebSocketMessage.class);
    }

    @Override
    public boolean willDecode(String stringMessage) {
        return stringMessage != null;
    }

}
