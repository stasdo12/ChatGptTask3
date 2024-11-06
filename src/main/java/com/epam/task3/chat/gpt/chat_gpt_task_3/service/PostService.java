package com.epam.task3.chat.gpt.chat_gpt_task_3.service;


import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Post;
import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;


    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post createPost(Long userId, Post post) {
        User user = userService.getByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        post.setAuthor(user);
        return postRepository.save(post);
    }

    public void likePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        User user = userService.getByUserId(userId).orElseThrow(() -> new RuntimeException("User not found"));
        post.getLikedBy().add(user);
        postRepository.save(post);
    }
}
