package com.napfernandes.chat.conversation.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.napfernandes.chat.conversation.enums.ConversationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationInput {
    @Min(2)
    @NotEmpty
    private List<String> members;

    @NotNull
    private ConversationType type;
}
