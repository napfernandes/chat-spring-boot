package com.napfernandes.chat.user;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {

    @MongoId
    private String id;
    private String email;
    private String password;
    private String salt;
    private Date createdAt;
    private Date updatedAt;
}
