package com.epam.task3.chat.gpt.chat_gpt_task_3.repo;

import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}