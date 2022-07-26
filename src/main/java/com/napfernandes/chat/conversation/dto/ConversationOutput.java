package com.napfernandes.chat.conversation.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.napfernandes.chat.conversation.enums.ConversationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationOutput {
    private String id;
    private List<String> members;
    private ConversationType type;
    private String hash;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
