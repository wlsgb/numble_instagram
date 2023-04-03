package com.instagram.numble_instagram.controller.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

	/**
	 * 본인 계정 정보 조회
	 */
	@GetMapping(value = "/me")
	public ResponseEntity<?> getUser(
			@AuthenticationPrincipal UserDetails userDetails
	) {
		return ResponseEntity.ok(userService.getUserByNickname(userDetails.getUsername()));
	}

}
