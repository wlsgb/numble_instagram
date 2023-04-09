
package com.instagram.numble_instagram.model.dto.user.request;

import org.springframework.web.multipart.MultipartFile;

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
public class UserModifyRequest {
	// 회원 Id
	private Long userId;
	// 닉네임
	private String nickname;
	// 프로필 사진
	private MultipartFile profileImage;
}
