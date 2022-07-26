package com.napfernandes.chat.websocket;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ChatService chatService;

    private static Gson gson = new Gson();

    @MessageMapping("/messages/{conversationId}")
    @SendTo("/topic/messages/{conversationId}")
    public WebSocketMessage send(@DestinationVariable String conversationId, @Payload WebSocketMessage message)
            throws Exception {
        return this.chatService.sendMessageToConversation(conversationId, message);
    }

    @MessageMapping("/messages")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(
            @DestinationVariable String conversationId,
            @Payload String message,
            Principal principal) throws Exception {
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
