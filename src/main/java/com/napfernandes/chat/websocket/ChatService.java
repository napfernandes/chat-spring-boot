package com.napfernandes.chat.websocket;

import com.napfernandes.chat.conversation.exception.ConversationNotFoundException;

public interface ChatService {
    WebSocketMessage sendMessageToConversation(String conversationId, WebSocketMessage message)
            throws ConversationNotFoundException;
}
