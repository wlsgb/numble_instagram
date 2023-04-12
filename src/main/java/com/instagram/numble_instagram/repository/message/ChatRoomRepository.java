package com.instagram.numble_instagram.repository.message;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
