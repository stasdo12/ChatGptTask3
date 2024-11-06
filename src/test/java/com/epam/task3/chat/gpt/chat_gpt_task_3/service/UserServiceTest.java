package com.epam.task3.chat.gpt.chat_gpt_task_3.service;

import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import com.epam.task3.chat.gpt.chat_gpt_task_3.repo.UserRepository;
import com.epam.task3.chat.gpt.chat_gpt_task_3.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;
    private User targetUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setEmail("test@example.com");

        targetUser = new User();
        targetUser.setId(2L);
        targetUser.setUsername("targetUser");
        targetUser.setEmail("target@example.com");
    }

    @Test
    void registerUser_ShouldReturnNewUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.registerUser(user.getUsername(), user.getEmail());

        assertNotNull(createdUser);
        assertEquals("testUser", createdUser.getUsername());
        assertEquals("test@example.com", createdUser.getEmail());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void followUser_ShouldAddTargetUserToFollowingList() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findById(targetUser.getId())).thenReturn(Optional.of(targetUser));

        userService.followUser(user.getId(), targetUser.getId());

        assertTrue(user.getFollowing().contains(targetUser));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void followUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                userService.followUser(user.getId(), targetUser.getId()));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void followUser_ShouldThrowException_WhenTargetUserNotFound() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findById(targetUser.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                userService.followUser(user.getId(), targetUser.getId()));

        assertEquals("Target user not found", exception.getMessage());
        verify(userRepository, never()).save(user);
    }

    @Test
    void findUserByUsername_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.findUserByUsername(user.getUsername());

        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }

    @Test
    void findUserByUsername_ShouldReturnEmpty_WhenUserNotExists() {
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.findUserByUsername(user.getUsername());

        assertFalse(foundUser.isPresent());

        verify(userRepository, times(1)).findByUsername(user.getUsername());
    }
}