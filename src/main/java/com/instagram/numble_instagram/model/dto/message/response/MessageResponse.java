package com.instagram.numble_instagram.model.dto.message.response;


import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.message.Message;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {

    private String content;
    private UserResponse sendUser;
    private LocalDateTime regDate;

    public static MessageResponse convertResponse(Message message) {
        return MessageResponse.builder()
                .content(message.getContent())
                .sendUser(UserResponse.convertResponse(message.getSendUser()))
                .regDate(message.getRegDate())
                .build();
    }
}
