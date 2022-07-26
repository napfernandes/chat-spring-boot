package com.napfernandes.chat.user;

import java.util.List;

import com.napfernandes.chat.crypto.exception.RandomValueNumberOfBytesException;
import com.napfernandes.chat.user.dto.UserInput;
import com.napfernandes.chat.user.dto.UserOutput;
import com.napfernandes.chat.user.exception.UserAlreadyExistsException;

public interface UserService {
    List<UserOutput> findAllUsers();

    UserOutput getUserById(String userId);

    UserOutput insertUser(UserInput input) throws RandomValueNumberOfBytesException, UserAlreadyExistsException;
}
