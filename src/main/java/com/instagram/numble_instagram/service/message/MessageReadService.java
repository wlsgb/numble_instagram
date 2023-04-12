package com.instagram.numble_instagram.service.message;

import com.instagram.numble_instagram.exception.notFound.MessageNotFoundException;
import com.instagram.numble_instagram.model.entity.message.Message;
import com.instagram.numble_instagram.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MessageReadService {

    private final MessageRepository messageRepository;

    /**
     * 메세지 조회
     */
    public Message getMessage(Long msgId) {
        return messageRepository.findById(msgId)
                .orElseThrow(MessageNotFoundException::new);
    }
}
