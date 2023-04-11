package com.instagram.numble_instagram.controller.feed;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.feed.request.CommentModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.CommentRegisterRequest;
import com.instagram.numble_instagram.model.dto.feed.response.CommentResponse;
import com.instagram.numble_instagram.usecase.post.CreateCommentUseCase;
import com.instagram.numble_instagram.usecase.post.DeleteCommentUseCase;
import com.instagram.numble_instagram.usecase.post.UpdateCommentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/feed/comment")
public class CommentController {

	private final CreateCommentUseCase createCommentUseCase;
	private final UpdateCommentUseCase updateCommentUseCase;
	private final DeleteCommentUseCase deleteCommentUseCase;

	/**
	 * 댓글 생성
	 */
	@PostMapping(value = "")
	public ResponseEntity<CommentResponse> createComment(
		@RequestBody CommentRegisterRequest commentRegisterRequest,
		@AuthenticationPrincipal SecurityUser user
	) {
		return ResponseEntity.ok(createCommentUseCase.execute(user.getUser().getUserId(), commentRegisterRequest));
	}

	/**
	 * 댓글 수정
	 */
	@PutMapping(value = "")
	public ResponseEntity<CommentResponse> updateComment(
		@RequestBody CommentModifyRequest commentModifyRequest,
		@AuthenticationPrincipal SecurityUser user
	) {
		return ResponseEntity.ok(updateCommentUseCase.execute(user.getUser().getUserId(), commentModifyRequest));
	}

	/**
	 * 댓글 삭제
	 */
	@DeleteMapping(value = "/{commentId}")
	public ResponseEntity<HttpStatus> deleteComment(
		@PathVariable Long commentId,
		@AuthenticationPrincipal SecurityUser user
	) {
		deleteCommentUseCase.execute(user.getUser().getUserId(), commentId);
		return ResponseEntity.ok().build();
	}

}
