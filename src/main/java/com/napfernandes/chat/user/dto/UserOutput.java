package com.napfernandes.chat.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserOutput {
    private String id;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
