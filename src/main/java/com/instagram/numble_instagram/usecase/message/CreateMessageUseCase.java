package com.instagram.numble_instagram.usecase.message;

import com.instagram.numble_instagram.model.dto.message.request.MessageSendRequest;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.message.*;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateMessageUseCase {

    private final UserService userService;
    private final ChatRoomReadService chatRoomReadService;
    private final ChatRoomWriteService chatRoomWriteService;
    private final UserChatRoomMappedReadService chatRoomUserReadService;
    private final MessageReadService messageReadService;
    private final MessageWriteService messageWriteService;

    @Transactional
    public void execute(Long fromUserId, MessageSendRequest messageSendRequest) {
        User fromUser = userService.getUser(fromUserId);
        User toUser = userService.getUser(messageSendRequest.toUserId());
    }
}
