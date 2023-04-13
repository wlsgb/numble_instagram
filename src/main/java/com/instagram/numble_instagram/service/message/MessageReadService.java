package com.instagram.numble_instagram.service.message;

import com.instagram.numble_instagram.model.dto.CursorResult;
import com.instagram.numble_instagram.model.dto.message.response.MessageResponse;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.Message;
import com.instagram.numble_instagram.repository.message.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MessageReadService {

    private final MessageRepository messageRepository;

    /**
     * 메세지 목록 조회
     */
    public CursorResult<MessageResponse> getMessageList(Long msgId, ChatRoom chatRoom, Pageable pageable) {
        final List<Message> messageList = getPagingMessageList(msgId, chatRoom, pageable);
        final Long lastMsgId = messageList.isEmpty() ?
                null : messageList.get(messageList.size() - 1).getMsgId();

        return CursorResult.<MessageResponse>builder()
                .list(messageList.stream()
                        .map(MessageResponse::convertResponse)
                        .collect(Collectors.toList()))

                .hasNext(hasNext(msgId, chatRoom))
                .build();
    }

    /**
     * 페이징 된 메세지 목록 조회
     */
    private List<Message> getPagingMessageList(Long msgId, ChatRoom chatRoom, Pageable pageable) {
        return msgId == null ?
                messageRepository.findAllByChatRoomOrderByMsgIdDesc(chatRoom, pageable) :
                messageRepository.findByMsgIdAndChatRoomLessThanOrderByMsgIdDesc(msgId, chatRoom, pageable);
    }

    /**
     * 다음 메세지 존재 여부
     */
    private boolean hasNext(Long msgId, ChatRoom chatRoom) {
        if (msgId == null)
            return false;
        return messageRepository.existsByMsgIdAndChatRoomLessThan(msgId, chatRoom);
    }

}
