package com.instagram.numble_instagram.controller.dm;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.message.request.MessageSendRequest;
import com.instagram.numble_instagram.usecase.message.CreateMessageUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/dm")
public class DmController {
	private final CreateMessageUseCase createMessageUseCase;

	@PostMapping(value = "")
	public ResponseEntity<HttpStatus> sendMessage(
		@Validated @RequestBody MessageSendRequest messageSendRequest,
		@AuthenticationPrincipal SecurityUser user
	) {
		createMessageUseCase.execute(user.getUser().getUserId(), messageSendRequest);
		return ResponseEntity.ok().build();
	}
}
