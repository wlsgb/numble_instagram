package com.instagram.numble_instagram.controller.feed;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.feed.request.ReplyModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.ReplyRegisterRequest;
import com.instagram.numble_instagram.model.dto.feed.response.ReplyResponse;
import com.instagram.numble_instagram.usecase.post.CreateReplyUseCase;
import com.instagram.numble_instagram.usecase.post.DeleteReplyUseCase;
import com.instagram.numble_instagram.usecase.post.UpdateReplyUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/feed/reply")
public class ReplyController {

	private final CreateReplyUseCase createReplyUseCase;
	private final UpdateReplyUseCase updateReplyUseCase;
	private final DeleteReplyUseCase deleteReplyUseCase;

	/**
	 * 답글 생성
	 */
	@PostMapping(value = "")
	public ResponseEntity<ReplyResponse> saveReply(
		@RequestBody ReplyRegisterRequest replyRegisterRequest,
		@AuthenticationPrincipal SecurityUser user
	) {
		return ResponseEntity.ok(createReplyUseCase.execute(user.getUser().getUserId(), replyRegisterRequest));
	}

	/**
	 * 답글 수정
	 */
	@PutMapping(value = "")
	public ResponseEntity<ReplyResponse> modifyReply(
		@RequestBody ReplyModifyRequest replyModifyRequest,
		@AuthenticationPrincipal SecurityUser user
	) {
		return ResponseEntity.ok(updateReplyUseCase.execute(user.getUser().getUserId(), replyModifyRequest));
	}

	/**
	 * 답글 삭제
	 */
	@DeleteMapping(value = "/{replyId}")
	public ResponseEntity<HttpStatus> deleteReply(
		@PathVariable Long replyId,
		@AuthenticationPrincipal SecurityUser user
	) {
		deleteReplyUseCase.execute(user.getUser().getUserId(), replyId);
		return ResponseEntity.ok().build();
	}

}
