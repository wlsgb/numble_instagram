package com.instagram.numble_instagram.model.dto.user.response;

import com.instagram.numble_instagram.model.entity.user.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProfileResponse {
    private String nickname;
    private String profileImageUrl;
    private long follower;
    private long following;

    public static ProfileResponse convertResponse(User user, long follower, long following) {
        return ProfileResponse.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .follower(follower)
                .following(following)
                .build();
    }
}
