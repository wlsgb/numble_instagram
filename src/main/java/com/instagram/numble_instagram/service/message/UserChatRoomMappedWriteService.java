package com.instagram.numble_instagram.service.message;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instagram.numble_instagram.exception.notFound.UserChatRoomMappedNotFoundException;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.UserChatRoomMapped;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.message.UserChatRoomMappedRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserChatRoomMappedWriteService {
    private final UserChatRoomMappedRepository chatRoomUserRepository;

    /**
     * 채팅인원 추가
     */
    public UserChatRoomMapped plusUser(ChatRoom chatRoom, User user) {
        try {
            return getUserChatRoomMapped(chatRoom, user);
        } catch (UserChatRoomMappedNotFoundException e) {
            UserChatRoomMapped newChatRoom = UserChatRoomMapped.plusUser(chatRoom, user);
            return chatRoomUserRepository.save(newChatRoom);
        }
    }

    /**
     * 매핑 정보 조회
     */
    private UserChatRoomMapped getUserChatRoomMapped(ChatRoom chatRoom, User user) {
        return chatRoomUserRepository.findByChatRoomAndChatUser(chatRoom, user)
            .orElseThrow(UserChatRoomMappedNotFoundException::new);
    }
}
