package com.napfernandes.chat.conversation.dto.message.action;

import java.time.LocalDateTime;

import com.napfernandes.chat.conversation.enums.MessageActionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageActionOutput {
    private String userId;
    private MessageActionType actionType;
    private LocalDateTime createdAt;
}
