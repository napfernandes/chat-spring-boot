package com.napfernandes.chat.conversation.dto.message.action;

import com.napfernandes.chat.conversation.enums.MessageActionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageActionInput {
    private String userId;
    private MessageActionType actionType;
}
