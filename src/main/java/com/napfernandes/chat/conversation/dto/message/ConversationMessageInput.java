package com.napfernandes.chat.conversation.dto.message;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversationMessageInput {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String message;
}
