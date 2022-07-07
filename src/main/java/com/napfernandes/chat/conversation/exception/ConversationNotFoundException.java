package com.napfernandes.chat.conversation.exception;

public class ConversationNotFoundException extends Exception {
    public ConversationNotFoundException(String conversationIdOrHash) {
        super("Conversation (%s) not found.".formatted(conversationIdOrHash));
    }
}
