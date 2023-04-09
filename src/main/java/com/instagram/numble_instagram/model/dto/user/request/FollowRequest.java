package com.instagram.numble_instagram.model.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FollowRequest {
	// 본인 유저 ID
	private Long userId;
	// 팔로우 대상자 ID
	private Long followUserId;
}
