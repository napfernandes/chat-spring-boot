package com.napfernandes.chat.conversation.entity;

import java.util.Date;

import com.napfernandes.chat.conversation.enums.MessageActionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageAction {
    private String userId;

    private MessageActionType actionType;
    private Date createdAt;
}