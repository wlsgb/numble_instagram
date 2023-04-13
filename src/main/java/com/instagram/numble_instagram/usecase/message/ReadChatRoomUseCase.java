package com.instagram.numble_instagram.usecase.message;

import com.instagram.numble_instagram.model.dto.message.response.ChatRoomResponse;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.message.UserChatRoomMappedReadService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadChatRoomUseCase {

    private final UserService userService;
    private final UserChatRoomMappedReadService userChatRoomMappedReadService;
    @Transactional(readOnly = true)
    public List<ChatRoomResponse> execute(Long userId) {
        User user = userService.getUser(userId);
        List<ChatRoom> chatRoomList = userChatRoomMappedReadService.getChatRoomList(user);
        return chatRoomList.stream()
                .map(ChatRoomResponse::convertResponse).toList();
    }
}
