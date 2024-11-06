package com.epam.task3.chat.gpt.chat_gpt_task_3.controller;

import com.epam.task3.chat.gpt.chat_gpt_task_3.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{userId}/like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable Long userId, @PathVariable Long postId) {
        likeService.likePost(userId, postId);
        return new ResponseEntity<>("Post liked successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/unlike/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long userId, @PathVariable Long postId) {
        likeService.unlikePost(userId, postId);
        return new ResponseEntity<>("Post unliked successfully", HttpStatus.OK);
    }
}