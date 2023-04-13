package com.instagram.numble_instagram.service.message;

import com.instagram.numble_instagram.exception.invalidRequest.NotChatRoomUserException;
import com.instagram.numble_instagram.exception.notFound.ChatRoomNotFoundException;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.message.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatRoomReadService {
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 조회
     */
    public ChatRoom getChatRoom(User user, Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);
        checkExistUser(user, chatRoom);
        return chatRoom;
    }

    private void checkExistUser(User user, ChatRoom chatRoom) {
        chatRoom.getUserChatRoomMappedList().stream()
                .filter(userChatRoomMapped -> userChatRoomMapped.getChatUser().equals(user))
                .findFirst()
                .orElseThrow(NotChatRoomUserException::new);
    }
}
