package com.instagram.numble_instagram.service.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.instagram.numble_instagram.model.dto.user.SignInRequest;
import com.instagram.numble_instagram.model.dto.user.SignUpRequest;
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
	public UserEntity signUp(SignUpRequest dto) {
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
	public UserEntity signIn(SignInRequest dto) {
		Optional<UserEntity> optiUser = userRepository.findByNickname(dto.getNickname());
		// 유저 정보가 없는 경우
		if (optiUser.isEmpty())
			throw new RuntimeException("해당 닉네임이 존재하지 않아 로그인에 실패하였습니다.");
		// 로그인 정보 리턴
		return optiUser.get();
	}
}
