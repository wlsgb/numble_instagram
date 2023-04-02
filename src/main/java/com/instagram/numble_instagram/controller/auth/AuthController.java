package com.instagram.numble_instagram.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.config.jwt.JwtTokenProvider;
import com.instagram.numble_instagram.model.dto.jwt.Token;
import com.instagram.numble_instagram.model.dto.user.SignInRequest;
import com.instagram.numble_instagram.model.dto.user.SignUpRequest;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.service.jwt.JwtService;
import com.instagram.numble_instagram.service.user.UserAuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	@PostMapping("/sign-up")
	public ResponseEntity<?> getSignInToken(@RequestBody SignUpRequest signUpRequest) {
		// 회원 가입 처리
		UserEntity user = userAuthService.signUp(signUpRequest);
		// 회원 가입 검증
		if (user == null || !StringUtils.hasText(user.getUserId().toString()))
			throw new RuntimeException("회원가입에 실패하였습니다.");
		// 성공
		return ResponseEntity.ok().build();
	}

	/**
	 * 로그인
	 */
	@PostMapping("/sign-in")
	public ResponseEntity<Token> getSignInToken(
		HttpServletResponse response,
		@RequestBody SignInRequest signInRequest
	) {
		log.info("로그인 요청 - [닉네임: {}]", signInRequest.getNickname());
		UserEntity user = userAuthService.signIn(signInRequest);

		Token token = jwtTokenProvider.createToken(
			String.valueOf(user.getUserId()),
			user.getNickname()
		);
		log.debug("로그인 완료 - [닉네임: {}]", signInRequest.getNickname());
		jwtService.signIn(token);
		response.addHeader("Authorization", "Bearer " + token.getAccessToken());
		response.addHeader("Authorization_refresh", "Bearer " + token.getRefreshToken());
		return ResponseEntity.ok(token);
	}

	@PostMapping(value = "/delete-account")
	public ResponseEntity<?> deleteAccount(
		HttpServletRequest request
	) {
		return ResponseEntity.ok().build();
	}
}