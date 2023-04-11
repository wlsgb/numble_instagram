package com.instagram.numble_instagram.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.message.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
