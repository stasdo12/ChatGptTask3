package com.epam.task3.chat.gpt.chat_gpt_task_3.service;

import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Post;
import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.PostRepository;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.UserRepository;
import com.epam.task3.chat.gpt.chat_gpt_task_3.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    private User user;
    private Post post1;
    private Post post2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("test_user");

        post1 = new Post();
        post1.setId(1L);
        post1.setTitle("First Post");
        post1.setBody("This is the first post");
        post1.setAuthor(user);

        post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Second Post");
        post2.setBody("This is the second post");
        post2.setAuthor(user);
    }

    @Test
    void createPost_ShouldReturnPost_WhenUserExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenReturn(post1);

        Post result = postService.createPost(user.getId(), post1.getTitle(), post1.getBody());

        assertNotNull(result);
        assertEquals("First Post", result.getTitle());
        assertEquals("This is the first post", result.getBody());
        assertEquals(user, result.getAuthor());

        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void createPost_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                postService.createPost(user.getId(), post1.getTitle(), post1.getBody()));

        assertEquals("User not found", exception.getMessage());
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void getPostsByUser_ShouldReturnPosts_WhenPostsExist() {
        when(postRepository.findAllByAuthorId(user.getId())).thenReturn(Arrays.asList(post1, post2));

        List<Post> posts = postService.getPostsByUser(user.getId());

        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals("First Post", posts.get(0).getTitle());
        assertEquals("Second Post", posts.get(1).getTitle());

        verify(postRepository, times(1)).findAllByAuthorId(user.getId());
    }

    @Test
    void getPostsByUser_ShouldReturnEmptyList_WhenNoPostsExist() {
        when(postRepository.findAllByAuthorId(user.getId())).thenReturn(Arrays.asList());

        List<Post> posts = postService.getPostsByUser(user.getId());

        assertNotNull(posts);
        assertTrue(posts.isEmpty());

        verify(postRepository, times(1)).findAllByAuthorId(user.getId());
    }

    @Test
    void getAllPosts_ShouldReturnAllPosts() {
        when(postRepository.findAll()).thenReturn(Arrays.asList(post1, post2));

        List<Post> posts = postService.getAllPosts();

        assertNotNull(posts);
        assertEquals(2, posts.size());
        assertEquals("First Post", posts.get(0).getTitle());
        assertEquals("Second Post", posts.get(1).getTitle());

        verify(postRepository, times(1)).findAll();
    }
}