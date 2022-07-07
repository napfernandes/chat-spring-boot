package com.napfernandes.chat.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.napfernandes.chat.user.dto.UserInput;
import com.napfernandes.chat.user.dto.UserOutput;
import com.napfernandes.chat.user.exception.UserAlreadyExistsException;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<UserOutput> findUsers() {
        return this.userService.findAllUsers();
    }

    @PostMapping("/users")
    public UserOutput insertUser(@RequestBody UserInput userInput) throws UserAlreadyExistsException {
        return this.userService.insertUser(userInput);
    }
}
