package com.epam.task3.chat.gpt.chat_gpt_task_3.controller;

import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Post;
import com.epam.task3.chat.gpt.chat_gpt_task_3.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/{userId}/create")
    public ResponseEntity<Post> createPost(@PathVariable Long userId, @RequestBody Post post) {
        Post createdPost = postService.createPost(userId, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @PostMapping("/{postId}/like/{userId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.likePost(userId, postId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
