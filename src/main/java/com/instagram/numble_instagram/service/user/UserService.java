package com.instagram.numble_instagram.service.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	/**
	 * 회원키로 회원 조회
	 */
	@Transactional
	public UserResponse getUserById(Long userId) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
		return UserResponse.convertResponse(user);
	}

	/**
	 * 닉네임으로 회원 조회
	 */
	@Transactional
	public UserResponse getUserByNickname(String nickname) {
		UserEntity user = userRepository.findByNickname(nickname)
				.orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
		return UserResponse.convertResponse(user);
	}
}
