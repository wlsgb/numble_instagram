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
import com.instagram.numble_instagram.model.dto.feed.request.ReplyModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.ReplySaveRequest;
import com.instagram.numble_instagram.model.dto.feed.response.ReplyResponse;
import com.instagram.numble_instagram.service.feed.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/feed/reply")
public class ReplyController {

	private final ReplyService replyService;

	/**
	 * 답글 생성
	 */
	@PostMapping()
	public ResponseEntity<ReplyResponse> saveReply(
		@RequestBody ReplySaveRequest dto,
		@AuthenticationPrincipal SecurityUser user
	) {
		dto.setUserId(user.getUser().getUserId());
		return ResponseEntity.ok(replyService.saveReply(dto));
	}

	/**
	 * 답글 수정
	 */
	@PutMapping()
	public ResponseEntity<ReplyResponse> modifyReply(
		@RequestBody ReplyModifyRequest dto
	) {
		return ResponseEntity.ok(replyService.modifyReply(dto));
	}

	/**
	 * 답글 삭제
	 */
	@DeleteMapping()
	public ResponseEntity<HttpStatus> deleteReply(
		@RequestBody Long replyId
	) {
		replyService.deleteReply(replyId);
		return ResponseEntity.ok().build();
	}

}
