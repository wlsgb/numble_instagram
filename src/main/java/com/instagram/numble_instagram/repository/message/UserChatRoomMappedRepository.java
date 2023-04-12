package com.instagram.numble_instagram.repository.message;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.UserChatRoomMapped;
import com.instagram.numble_instagram.model.entity.user.User;

@Repository
public interface UserChatRoomMappedRepository extends JpaRepository<UserChatRoomMapped, Long> {
    List<UserChatRoomMapped> findAllByChatUser(User chatUser);
    List<UserChatRoomMapped> findAllByChatUserOrChatUser(User fromUser, User toUser);
    Optional<UserChatRoomMapped> findByChatRoomAndChatUser(ChatRoom chatRoom, User fromUser);
    List<UserChatRoomMapped> findAllByChatRoom(ChatRoom chatRoom);
}
