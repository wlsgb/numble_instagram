package com.instagram.numble_instagram.service.message;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.UserChatRoomMapped;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.message.UserChatRoomMappedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        UserChatRoomMapped newChatRoom = UserChatRoomMapped.plusUser(chatRoom, user);
        return chatRoomUserRepository.save(newChatRoom);
    }
}
