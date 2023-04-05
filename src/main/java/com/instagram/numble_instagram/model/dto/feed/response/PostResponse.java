package com.instagram.numble_instagram.model.dto.feed.response;

import java.time.LocalDateTime;
import java.util.List;

import com.instagram.numble_instagram.model.dto.image.response.ImageResponse;
import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.feed.PostEntity;

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
public class PostResponse {
	private Long postId;
	private String content;
	private ImageResponse image;
	private UserResponse regUser;
	private LocalDateTime regDate;
	private LocalDateTime updDate;
	private List<CommentResponse> commentList;

	public static PostResponse convertResponse(PostEntity post) {
		if (post == null)
			return new PostResponse();

		return PostResponse.builder()
			.postId(post.getPostId())
			.content(post.getContent())
			.image(ImageResponse.convertResponse(post.getImage()))
			.regUser(UserResponse.convertResponse(post.getRegUser()))
			.regDate(post.getRegDate())
			.updDate(post.getUpdDate())
			.commentList(
				post.getCommentList().stream()
					.map(CommentResponse::convertResponse)
					.toList()
			)
			.build();
	}
}
