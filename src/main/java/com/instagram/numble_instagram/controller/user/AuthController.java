package com.instagram.numble_instagram.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.config.jwt.JwtTokenProvider;
import com.instagram.numble_instagram.model.dto.user.SignInRequest;
import com.instagram.numble_instagram.model.dto.user.SignUpRequest;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.service.user.UserAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AuthController {
	private final JwtTokenProvider jwtTokenProvider;
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
	public String getSignInToken(@RequestBody SignInRequest signInRequest) {
		UserEntity user = userAuthService.signIn(signInRequest);
		return jwtTokenProvider.createToken(user);
	}
}