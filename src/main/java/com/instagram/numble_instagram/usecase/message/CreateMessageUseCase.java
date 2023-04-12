package com.instagram.numble_instagram.usecase.message;

import com.instagram.numble_instagram.model.dto.message.request.MessageSendRequest;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.message.*;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateMessageUseCase {

    private final UserService userService;
    private final ChatRoomReadService chatRoomReadService;
    private final ChatRoomWriteService chatRoomWriteService;
    private final UserChatRoomMappedReadService userChatRoomMappedReadService;
    private final MessageReadService messageReadService;
    private final MessageWriteService messageWriteService;

    @Transactional
    public void execute(Long fromUserId, MessageSendRequest messageSendRequest) {
        User fromUser = userService.getUser(fromUserId);
        User toUser = userService.getUser(messageSendRequest.toUserId());

        List<ChatRoom> fromUserChatRoomList = userChatRoomMappedReadService.getChatRoomList(fromUser);
        List<ChatRoom> toUserChatRoomList = userChatRoomMappedReadService.getChatRoomList(toUser);

        ChatRoom chatRoom = fromUserChatRoomList.stream()
                .filter(fromChatRoom -> toUserChatRoomList.stream().anyMatch(toChatRoom -> toChatRoom.equals(fromChatRoom)))
                .findFirst()
                .orElse(chatRoomWriteService.register(fromUser));

        messageWriteService.sendMessage(chatRoom, fromUser, messageSendRequest.content());
        chatRoomWriteService.saveLastMessage(chatRoom.getChatRoomId(), messageSendRequest.content());
    }
}
