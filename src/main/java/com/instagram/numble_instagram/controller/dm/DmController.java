package com.instagram.numble_instagram.controller.dm;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.CursorResult;
import com.instagram.numble_instagram.model.dto.message.request.MessageSendRequest;
import com.instagram.numble_instagram.model.dto.message.response.ChatRoomResponse;
import com.instagram.numble_instagram.model.dto.message.response.MessageResponse;
import com.instagram.numble_instagram.usecase.message.CreateMessageUseCase;
import com.instagram.numble_instagram.usecase.message.ReadChatRoomUseCase;
import com.instagram.numble_instagram.usecase.message.ReadMessageUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/dm")
public class DmController {
	private final CreateMessageUseCase createMessageUseCase;
	private final ReadMessageUseCase readMessageUseCase;
	private final ReadChatRoomUseCase readChatRoomUseCase;

	/**
	 * 채팅룸 조회
	 */
	@GetMapping(value = "")
	public ResponseEntity<List<ChatRoomResponse>> getChatRoomList(
			@AuthenticationPrincipal SecurityUser user
	) {
		return ResponseEntity.ok(readChatRoomUseCase.execute(user.getUser().getUserId()));
	}

	/**
	 * 채팅룸 상세 조회
	 */
	@GetMapping(value = "/{chatRoomId}")
	public ResponseEntity<CursorResult<MessageResponse>> getChatRoomDetail(
			@PathVariable Long chatRoomId,
			@RequestParam(required = false) Long cursorId,
			@AuthenticationPrincipal SecurityUser user
	) {
		return ResponseEntity.ok(readMessageUseCase.execute(user.getUser().getUserId(), chatRoomId, cursorId));
	}

	/**
	 * DM 전송
	 */
	@PostMapping(value = "/send")
	public ResponseEntity<HttpStatus> sendMessage(
		@Validated @RequestBody MessageSendRequest messageSendRequest,
		@AuthenticationPrincipal SecurityUser user
	) {
		createMessageUseCase.execute(user.getUser().getUserId(), messageSendRequest);
		return ResponseEntity.ok().build();
	}
}
