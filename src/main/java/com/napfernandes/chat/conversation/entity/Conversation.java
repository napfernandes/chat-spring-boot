package com.napfernandes.chat.conversation.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.napfernandes.chat.conversation.enums.ConversationType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "conversations")
public class Conversation {

    @Id
    private String id;

    private List<String> members;
    private ConversationType type;
    private String hash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ConversationMessage> messages;

    public Conversation() {
        this.messages = new ArrayList<>();
    }

    public void addMessage(ConversationMessage message) {
        this.messages.add(message);
    }
}
