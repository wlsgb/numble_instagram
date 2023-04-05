package com.instagram.numble_instagram.model.dto.user.response;

import java.time.LocalDateTime;

import com.instagram.numble_instagram.model.dto.image.response.ImageResponse;
import com.instagram.numble_instagram.model.entity.user.UserEntity;

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
public class UserResponse {
	private Long userId;
	private String nickname;
	private ImageResponse image;
	private LocalDateTime regDate;
	private LocalDateTime updDate;

	public static UserResponse convertResponse(UserEntity user) {
		if (user == null)
			return new UserResponse();

		return UserResponse.builder()
			.userId(user.getUserId())
			.nickname(user.getNickname())
			.image(ImageResponse.convertResponse(user.getImage()))
			.regDate(user.getRegDate())
			.updDate(user.getUpdDate())
			.build();
	}
}
