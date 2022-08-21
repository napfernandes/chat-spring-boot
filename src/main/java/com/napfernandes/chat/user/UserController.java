package com.napfernandes.chat.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.napfernandes.chat.crypto.exception.RandomValueNumberOfBytesException;
import com.napfernandes.chat.user.dto.UserInput;
import com.napfernandes.chat.user.dto.UserOutput;
import com.napfernandes.chat.user.exception.UserAlreadyExistsException;
import com.napfernandes.chat.user.exception.UserNotFoundException;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserOutput> findUsers() {
        return this.userService.findAllUsers();
    }

    @GetMapping("/users/{userId}")
    public UserOutput getUserById(@PathVariable("userId") String userId) throws UserNotFoundException {
        return this.userService.getUserById(userId);
    }

    @PostMapping("/users")
    public UserOutput insertUser(@RequestBody UserInput userInput)
            throws RandomValueNumberOfBytesException, UserAlreadyExistsException {
        return this.userService.insertUser(userInput);
    }
}
