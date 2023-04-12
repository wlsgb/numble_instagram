package com.instagram.numble_instagram.service.message;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.UserChatRoomMapped;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.message.UserChatRoomMappedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserChatRoomMappedReadService {
    private final UserChatRoomMappedRepository chatRoomUserRepository;

    /**
     * 유저 정보로 매핑 조회
     */
    public List<UserChatRoomMapped> getUserChatRoomMappedList(User user) {
        return chatRoomUserRepository.findAllByChatUser(user);
    }

    /**
     * 채팅방 정보로 매핑 조회
     */
    public List<UserChatRoomMapped> getUserChatRoomMappedList(ChatRoom chatRoom) {
        return chatRoomUserRepository.findAllByChatRoom(chatRoom);
    }

    /**
     * 채팅방 조회
     */
    public List<ChatRoom> getChatRoomList(User user) {
        List<UserChatRoomMapped> chatRoomUserList = chatRoomUserRepository.findAllByChatUser(user);
        return new ArrayList<>(
                chatRoomUserList.stream()
                        .collect(Collectors.groupingBy(UserChatRoomMapped::getChatRoom))
                        .keySet()
        );
    }

    /**
     * 채팅방의 유저 목록 조회
     */
    public List<User> getChatRoomInUserList(ChatRoom chatRoom) {
        List<UserChatRoomMapped> chatRoomUserList = chatRoomUserRepository.findAllByChatRoom(chatRoom);
        return new ArrayList<>(
                chatRoomUserList.stream()
                        .collect(Collectors.groupingBy(UserChatRoomMapped::getChatUser))
                        .keySet()
        );
    }
}
