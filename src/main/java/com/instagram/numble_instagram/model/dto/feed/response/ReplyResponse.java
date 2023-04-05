package com.instagram.numble_instagram.model.dto.feed.response;

import java.time.LocalDateTime;

import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.feed.ReplyEntity;

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
public class ReplyResponse {
	private Long replyId;
	private String content;
	private UserResponse regUser;
	private LocalDateTime regDate;
	private LocalDateTime updDate;

	public static ReplyResponse convertResponse(ReplyEntity reply) {
		if (reply == null)
			return new ReplyResponse();

		return ReplyResponse.builder()
			.replyId(reply.getReplyId())
			.content(reply.getContent())
			.regUser(UserResponse.convertResponse(reply.getRegUser()))
			.regDate(reply.getRegDate())
			.updDate(reply.getUpdDate())
			.build();
	}
}
