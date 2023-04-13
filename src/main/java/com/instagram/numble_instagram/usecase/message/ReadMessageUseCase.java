package com.instagram.numble_instagram.usecase.message;

import com.instagram.numble_instagram.model.dto.CursorResult;
import com.instagram.numble_instagram.model.dto.message.response.MessageResponse;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.message.ChatRoomReadService;
import com.instagram.numble_instagram.service.message.MessageReadService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadMessageUseCase {

    @Value("${service.paging.default-size}")
    private int PAGING_DEFAULT_SIZE;
    private final UserService userService;
    private final ChatRoomReadService chatRoomReadService;
    private final MessageReadService messageReadService;

    @Transactional(readOnly = true)
    public CursorResult<MessageResponse> execute(Long userId, Long chatRoomId, Long cursorId) {
        User user = userService.getUser(userId);
        ChatRoom chatRoom = chatRoomReadService.getChatRoom(user, chatRoomId);
        return messageReadService.getMessageList(cursorId, chatRoom, PageRequest.of(0, PAGING_DEFAULT_SIZE));
    }
}
