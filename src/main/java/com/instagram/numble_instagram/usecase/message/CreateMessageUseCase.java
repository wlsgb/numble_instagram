package com.instagram.numble_instagram.usecase.message;

import com.instagram.numble_instagram.model.dto.message.request.MessageSendRequest;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.message.Message;
import com.instagram.numble_instagram.model.entity.message.UserChatRoomMapped;
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
    private final ChatRoomWriteService chatRoomWriteService;
    private final UserChatRoomMappedReadService userChatRoomMappedReadService;
    private final UserChatRoomMappedWriteService userChatRoomMappedWriteService;
    private final MessageWriteService messageWriteService;

    @Transactional
    public void execute(Long fromUserId, MessageSendRequest messageSendRequest) {
        User fromUser = userService.getUser(fromUserId);
        User toUser = userService.getUser(messageSendRequest.toUserId());
        // 채팅룸 존재여부 확인
        ChatRoom singleChatRoom = null;
        if (userChatRoomMappedReadService.existSingleChatRoom(fromUser, toUser))
            singleChatRoom = userChatRoomMappedReadService.getSingleChatRoom(fromUser, toUser);
        // 채팅룸 없는 경우
        if (singleChatRoom == null)
            singleChatRoom = chatRoomWriteService.register(fromUser);
        // 유저 채팅 매핑 테이블 추가
        UserChatRoomMapped fromRoomMapped = userChatRoomMappedWriteService.plusUser(singleChatRoom, fromUser);
        UserChatRoomMapped toRoomMapped = userChatRoomMappedWriteService.plusUser(singleChatRoom, toUser);
        singleChatRoom.addUserChatRoomMapped(fromRoomMapped);
        singleChatRoom.addUserChatRoomMapped(toRoomMapped);
        // 메세지 전송
        Message newMessage = messageWriteService.sendMessage(singleChatRoom, fromUser, messageSendRequest.content());
        singleChatRoom.addMessage(newMessage);
        singleChatRoom.changeLastMessage(newMessage.getContent());
        singleChatRoom.changeLastSendUser(newMessage.getSendUser());
    }
}
