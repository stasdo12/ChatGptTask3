package com.epam.task3.chat.gpt.chat_gpt_task_3.service;

import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Like;
import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Post;
import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.LikeRepository;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.PostRepository;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.UserRepository;
import com.epam.task3.chat.gpt.chat_gpt_task_3.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private LikeService likeService;

    private User user;
    private Post post;
    private Like like;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        post = new Post();
        post.setId(1L);

        like = new Like();
        like.setUser(user);
        like.setPost(post);
    }

    @Test
    void likePost_ShouldAddLike_WhenPostNotLiked() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());

        likeService.likePost(user.getId(), post.getId());

        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void likePost_ShouldThrowException_WhenPostAlreadyLiked() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(like));

        Exception exception = assertThrows(RuntimeException.class, () ->
                likeService.likePost(user.getId(), post.getId()));

        assertEquals("Post already liked by the user", exception.getMessage());
    }

    @Test
    void likePost_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                likeService.likePost(user.getId(), post.getId()));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void likePost_ShouldThrowException_WhenPostNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                likeService.likePost(user.getId(), post.getId()));

        assertEquals("Post not found", exception.getMessage());
    }

    @Test
    void unlikePost_ShouldRemoveLike_WhenLikeExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.of(like));

        likeService.unlikePost(user.getId(), post.getId());

        verify(likeRepository, times(1)).delete(like);
    }

    @Test
    void unlikePost_ShouldThrowException_WhenLikeNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        when(likeRepository.findByUserAndPost(user, post)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                likeService.unlikePost(user.getId(), post.getId()));

        assertEquals("Like not found", exception.getMessage());
    }

    @Test
    void unlikePost_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                likeService.unlikePost(user.getId(), post.getId()));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void unlikePost_ShouldThrowException_WhenPostNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.findById(post.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                likeService.unlikePost(user.getId(), post.getId()));

        assertEquals("Post not found", exception.getMessage());
    }
}
