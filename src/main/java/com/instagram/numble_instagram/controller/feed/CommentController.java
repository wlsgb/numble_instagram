package com.instagram.numble_instagram.controller.feed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.feed.request.CommentModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.CommentSaveRequest;
import com.instagram.numble_instagram.model.dto.feed.response.CommentResponse;
import com.instagram.numble_instagram.service.feed.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/feed/comment")
public class CommentController {

	private final CommentService commentService;

	/**
	 * 댓글 생성
	 */
	@PostMapping()
	public ResponseEntity<CommentResponse> saveComment(
		@RequestBody CommentSaveRequest dto,
		@AuthenticationPrincipal SecurityUser user
	) {
		dto.setUserId(user.getUser().getUserId());
		return ResponseEntity.ok(commentService.saveComment(dto));
	}

	/**
	 * 댓글 수정
	 */
	@PutMapping()
	public ResponseEntity<CommentResponse> modifyComment(
		@RequestBody CommentModifyRequest dto
	) {
		return ResponseEntity.ok(commentService.modifyComment(dto));
	}

	/**
	 * 댓글 삭제
	 */
	@DeleteMapping()
	public ResponseEntity<HttpStatus> deleteComment(
		@RequestBody Long commentId
	) {
		commentService.deleteComment(commentId);
		return ResponseEntity.ok().build();
	}

}
