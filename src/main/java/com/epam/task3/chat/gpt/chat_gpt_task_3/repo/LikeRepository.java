package com.epam.task3.chat.gpt.chat_gpt_task_3.repo;

import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Like;
import com.epam.task3.chat.gpt.chat_gpt_task_3.model.Post;
import com.epam.task3.chat.gpt.chat_gpt_task_3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
}