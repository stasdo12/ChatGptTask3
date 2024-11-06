package com.epam.task3.chat.gpt.chat_gpt_task_3.service;


import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Post;
import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.PostRepository;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(Long userId, String title, String body) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setAuthor(user);
        return postRepository.save(post);
    }

    public List<Post> getPostsByUser(Long userId) {
        return postRepository.findAllByAuthorId(userId);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}