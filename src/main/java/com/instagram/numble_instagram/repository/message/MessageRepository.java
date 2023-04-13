package com.instagram.numble_instagram.repository.message;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByChatRoomOrderByMsgIdDesc(ChatRoom chatRoom, Pageable pageable);

    List<Message> findByMsgIdAndChatRoomLessThanOrderByMsgIdDesc(Long msgId, ChatRoom chatRoom, Pageable pageable);

    boolean existsByMsgIdAndChatRoomLessThan(Long msgId, ChatRoom chatRoom);
}
