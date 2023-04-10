package com.instagram.numble_instagram.model.dto.user.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProfileResponse {
    private String nickname;
    private String profileImage;
    private long follower;
    private long following;

}
