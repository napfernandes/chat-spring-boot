package com.napfernandes.chat.conversation.dto.message;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationMessageInput {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String message;
}
