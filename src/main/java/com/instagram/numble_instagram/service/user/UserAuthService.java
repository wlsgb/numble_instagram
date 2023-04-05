package com.instagram.numble_instagram.service.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.instagram.numble_instagram.model.dto.user.request.LoginRequest;
import com.instagram.numble_instagram.model.dto.user.request.JoinRequest;
import com.instagram.numble_instagram.model.entity.image.ImageEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.image.ImageRepository;
import com.instagram.numble_instagram.repository.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserAuthService {

	private final UserRepository userRepository;
	private final ImageRepository imageRepository;

	/**
	 * 회원가입
	 */
	@Transactional
	public UserEntity joinUser(JoinRequest dto) {
		// 닉네임 검증
		Optional<UserEntity> validateUser = userRepository.findByNickname(dto.getNickname());
		// 동일한 닉네임이 존재하는 경우
		if (validateUser.isPresent())
			throw new RuntimeException("동일한 닉네임을 가진 유저가 존재합니다.");

		// 프로필 사진 업로드
		ImageEntity image = imageRepository.save(ImageEntity.builder()
			.imageUrl(String.valueOf(UUID.randomUUID()))
			.build());

		// 회원 가입
		return userRepository.save(UserEntity.builder()
			.nickname(dto.getNickname())
			.image(image)
			.build());
	}

	/**
	 * 로그인
	 */
	public UserEntity signIn(LoginRequest dto) {
		return userRepository.findByNickname(dto.getNickname())
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

	/**
	 * 계정 삭제 처리
	 */
	public void deleteAccount(String userId) {

		userRepository.deleteById(Long.valueOf(userId));
	}
}
