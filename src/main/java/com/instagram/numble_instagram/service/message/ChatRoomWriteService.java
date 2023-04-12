package com.instagram.numble_instagram.service.message;

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
@Transactional
public class ChatRoomWriteService {
    private final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅방 등록
     */
    public ChatRoom register(User user) {
        ChatRoom newChatRoom = ChatRoom.register(user);
        return chatRoomRepository.save(newChatRoom);
    }

    /**
     * 마지막 전송 메세지 저장
     */
    public ChatRoom saveLastMessage(Long chatRoomId, String content) {
        ChatRoom chatRoom = getChatRoom(chatRoomId);
        chatRoom.changeLastMessage(content);
        return chatRoom;
    }

    /**
     * 채팅방 조회
     */
    private ChatRoom getChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(ChatRoomNotFoundException::new);
    }
}
