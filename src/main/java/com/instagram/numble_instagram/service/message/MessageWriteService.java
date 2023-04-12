package com.instagram.numble_instagram.service.message;

import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.Message;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MessageWriteService {

    private final MessageRepository messageRepository;

    /**
     * 메세지 전송 처리
     */
    public Message sendMessage(ChatRoom chatRoom, User sendUser, String content) {
        Message newMessage = Message.sendMessage(content, chatRoom, sendUser);
        return messageRepository.save(newMessage);
    }
}
