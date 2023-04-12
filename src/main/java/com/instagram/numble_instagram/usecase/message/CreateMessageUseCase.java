package com.instagram.numble_instagram.usecase.message;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instagram.numble_instagram.model.dto.message.request.MessageSendRequest;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.message.ChatRoomReadService;
import com.instagram.numble_instagram.service.message.ChatRoomWriteService;
import com.instagram.numble_instagram.service.message.MessageReadService;
import com.instagram.numble_instagram.service.message.MessageWriteService;
import com.instagram.numble_instagram.service.message.UserChatRoomMappedReadService;
import com.instagram.numble_instagram.service.message.UserChatRoomMappedWriteService;
import com.instagram.numble_instagram.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateMessageUseCase {

    private final UserService userService;
    private final ChatRoomReadService chatRoomReadService;
    private final ChatRoomWriteService chatRoomWriteService;
    private final UserChatRoomMappedReadService userChatRoomMappedReadService;
    private final UserChatRoomMappedWriteService userChatRoomMappedWriteService;
    private final MessageReadService messageReadService;
    private final MessageWriteService messageWriteService;

    @Transactional
    public void execute(Long fromUserId, MessageSendRequest messageSendRequest) {
        User fromUser = userService.getUser(fromUserId);
        User toUser = userService.getUser(messageSendRequest.toUserId());

        List<ChatRoom> fromUserChatRoomList = userChatRoomMappedReadService.getSingleChatRoomList(fromUser, toUser);

        ChatRoom chatRoom = fromUserChatRoomList.stream()
            .findFirst()
            .orElse(chatRoomWriteService.register(fromUser));

        userChatRoomMappedWriteService.plusUser(chatRoom, fromUser);
        userChatRoomMappedWriteService.plusUser(chatRoom, toUser);
        messageWriteService.sendMessage(chatRoom, fromUser, messageSendRequest.content());
        chatRoomWriteService.saveLastMessage(chatRoom.getChatRoomId(), messageSendRequest.content());

    }
}
