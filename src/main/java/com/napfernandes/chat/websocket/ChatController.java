package com.napfernandes.chat.websocket;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;

@Controller
public class ChatController {
    private final Gson gson;
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.gson = new Gson();
        this.chatService = chatService;
    }

    @MessageMapping("/messages/{conversationId}")
    @SendTo("/topic/messages/{conversationId}")
    public WebSocketMessage send(
            @DestinationVariable String conversationId,
            @Payload WebSocketMessage message) throws Exception {
        return this.chatService.sendMessageToConversation(conversationId, message);
    }

    @MessageMapping("/messages")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(
            @DestinationVariable String conversationId,
            @Payload String message) throws Exception {
        return gson
                .fromJson(message, WebSocketMessage.class)
                .getFrom().toString();
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
