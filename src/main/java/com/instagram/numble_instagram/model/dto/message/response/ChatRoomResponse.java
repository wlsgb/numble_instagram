package com.instagram.numble_instagram.model.dto.message.response;


import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.message.ChatRoom;
import com.instagram.numble_instagram.model.entity.user.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoomResponse {
    private Long chatRoomId;
    private List<UserResponse> userList;
    private UserResponse lastSendUser;
    private String lastMessage;
    private LocalDateTime uptDate;

    public static ChatRoomResponse convertResponse(ChatRoom chatRoom) {
        return ChatRoomResponse.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .userList(
                        chatRoom.getUserChatRoomMappedList().stream()
                                .map(userChatRoomMapped -> {
                                    User user = userChatRoomMapped.getChatUser();
                                    return UserResponse.convertResponse(user);
                                })
                                .toList()
                )
                .lastSendUser(UserResponse.convertResponse(chatRoom.getLastSendUser()))
                .lastMessage(chatRoom.getLastMessage())
                .uptDate(chatRoom.getUpdDate())
                .build();
    }
}
