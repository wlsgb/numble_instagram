package com.instagram.numble_instagram.repository.message;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.UserChatRoomMapped;
import com.instagram.numble_instagram.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatRoomMappedRepository extends JpaRepository<UserChatRoomMapped, Long> {
    List<UserChatRoomMapped> findAllByChatUser(User chatUser);

    List<UserChatRoomMapped> findAllByChatRoom(ChatRoom chatRoom);
}
