package com.instagram.numble_instagram.model.dto.feed.response;

import java.time.LocalDateTime;
import java.util.List;

import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.feed.CommentEntity;

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
public class CommentResponse {
	private Long commentId;
	private String content;
	private UserResponse regUser;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	private List<ReplyResponse> replyList;

	public static CommentResponse convertResponse(CommentEntity comment) {
		if (comment == null)
			return new CommentResponse();

		return CommentResponse.builder()
			.commentId(comment.getCommentId())
			.content(comment.getContent())
			.regUser(UserResponse.convertResponse(comment.getRegUser()))
			.regDate(comment.getRegDate())
			.updDate(comment.getUpdDate())
			.replyList(
				comment.getReplyList().stream()
				.map(ReplyResponse::convertResponse)
				.toList()
			)
			.build();
	}
}
