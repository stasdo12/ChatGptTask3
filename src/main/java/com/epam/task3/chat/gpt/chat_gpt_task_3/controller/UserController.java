package com.epam.task3.chat.gpt.chat_gpt_task_3.controller;

import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import com.epam.task3.chat.gpt.chat_gpt_task_3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestParam String username, @RequestParam String email) {
        User user = userService.registerUser(username, email);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @PathVariable Long targetUserId) {
        userService.followUser(userId, targetUserId);
        return new ResponseEntity<>("User followed successfully", HttpStatus.OK);
    }
}