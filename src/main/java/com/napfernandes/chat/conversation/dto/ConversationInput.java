package com.napfernandes.chat.conversation.dto;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.napfernandes.chat.conversation.enums.ConversationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConversationInput {
    @Size(min = 2)
    @NotNull
    private List<String> members;

    @NotNull
    private ConversationType type;
}
