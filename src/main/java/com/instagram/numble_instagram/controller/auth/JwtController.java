package com.instagram.numble_instagram.controller.auth;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.model.dto.jwt.RefreshApiResponseMessage;
import com.instagram.numble_instagram.model.dto.jwt.Token;
import com.instagram.numble_instagram.service.jwt.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/jwt")
public class JwtController {
	private final JwtService jwtService;

	@PostMapping(value = "/refresh")
	public ResponseEntity<RefreshApiResponseMessage> validateRefreshToken(
		@RequestBody Token token
	) {
		log.info("refresh controller 실행");
		Map<String, String> map = jwtService.validateRefreshToken(token.getRefreshToken());

		RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);

		if (map.get("status").equals("402")) {
			log.info("RefreshController - Refresh Token이 만료.");
			return new ResponseEntity<>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
		}

		log.info("RefreshController - Refresh Token이 유효.");
		return ResponseEntity.ok(refreshApiResponseMessage);
	}
}
