package com.instagram.numble_instagram.model.dto.user.response;

import com.instagram.numble_instagram.model.entity.user.UserEntity;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long userId;
    private String nickname;
    private String profileImageUrl;
    private LocalDateTime regDate;
    private LocalDateTime updDate;

    public static UserResponse convertResponse(UserEntity user) {
        if (user == null)
            return new UserResponse();

        return UserResponse.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .regDate(user.getRegDate())
                .updDate(user.getUpdDate())
                .build();
    }
}
