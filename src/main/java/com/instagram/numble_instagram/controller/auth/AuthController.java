package com.instagram.numble_instagram.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.config.jwt.JwtTokenProvider;
import com.instagram.numble_instagram.model.dto.jwt.Token;
import com.instagram.numble_instagram.model.dto.user.JoinRequest;
import com.instagram.numble_instagram.model.dto.user.LoginRequest;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.service.jwt.JwtService;
import com.instagram.numble_instagram.service.user.UserAuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
	private final JwtTokenProvider jwtTokenProvider;
	private final JwtService jwtService;
	private final UserAuthService userAuthService;

	/**
	 * 회원가입
	 */
	@PostMapping("/join")
	public ResponseEntity<?> joinUser(@RequestBody JoinRequest joinRequest) {
		// 회원 가입 처리
		UserEntity user = userAuthService.joinUser(joinRequest);
		// 회원 가입 검증
		if (user == null || !StringUtils.hasText(user.getUserId().toString()))
			throw new RuntimeException("회원가입에 실패하였습니다.");
		// 성공
		return ResponseEntity.ok().build();
	}

	/**
	 * 로그인
	 */
	@PostMapping("/login")
	public ResponseEntity<Token> getLoginToken(
		@RequestBody LoginRequest signInRequest
	) {
		log.info("로그인 요청 - [닉네임: {}]", signInRequest.getNickname());
		UserEntity user = userAuthService.signIn(signInRequest);

		Token token = jwtTokenProvider.createToken(
			String.valueOf(user.getUserId()),
			user.getNickname()
		);
		log.debug("로그인 완료 - [닉네임: {}]", signInRequest.getNickname());
		jwtService.signIn(token);
		return ResponseEntity.ok(token);
	}

	@PostMapping(value = "/delete-account")
	public ResponseEntity<?> deleteAccount(
		HttpServletRequest request
	) {
		return ResponseEntity.ok().build();
	}
}