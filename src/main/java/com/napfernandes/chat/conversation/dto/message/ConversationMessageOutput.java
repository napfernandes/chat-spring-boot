package com.napfernandes.chat.conversation.dto.message;

import java.util.List;

import com.napfernandes.chat.conversation.dto.message.action.MessageActionOutput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationMessageOutput {
    private String id;
    private String userId;
    private String message;
    private List<MessageActionOutput> actions;
}