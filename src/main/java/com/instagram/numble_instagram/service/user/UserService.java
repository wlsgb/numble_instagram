package com.instagram.numble_instagram.service.user;

import org.springframework.stereotype.Service;

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
	public UserEntity getUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
	}

	/**
	 * 닉네임으로 회원 조회
	 */
	public UserEntity getUserByNickname(String nickname) {
		return userRepository.findByNickname(nickname)
				.orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
	}
}
