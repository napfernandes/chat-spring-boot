package com.napfernandes.chat.conversation.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversationMessage {
    @Id
    private String id;

    private String userId;
    private String message;
    private List<MessageAction> actions;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ConversationMessage() {
        this.actions = new ArrayList<>();
    }

    public void addAction(MessageAction action) {
        this.actions.add(action);
    }
}
