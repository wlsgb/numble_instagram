package com.instagram.numble_instagram.service.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.instagram.numble_instagram.model.dto.user.request.JoinRequest;
import com.instagram.numble_instagram.model.dto.user.request.LoginRequest;
import com.instagram.numble_instagram.model.dto.user.request.UserModifyRequest;
import com.instagram.numble_instagram.model.entity.image.ImageEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.user.UserRepository;
import com.instagram.numble_instagram.service.image.ImageService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

	private final UserRepository userRepository;
	private final ImageService imageService;

	/**
	 * 회원가입
	 */
	@Transactional
	public UserEntity joinUser(JoinRequest dto) {
		// 신규 유저
		UserEntity newUser = userRepository.findByNickname(dto.getNickname())
			.orElseThrow(() -> new RuntimeException("동일한 닉네임을 가진 유저가 존재합니다."));

		// 프로필 사진 업로드
		ImageEntity saveImage = null;
		if (dto.getProfileImage() != null) {
			// 이미지 저장
			saveImage = imageService.saveImage(dto.getProfileImage(), newUser);
		}

		// 회원 가입
		return userRepository.save(
			UserEntity.builder()
				.nickname(dto.getNickname())
				.image(saveImage)
				.build()
		);
	}

	/**
	 * 로그인
	 */
	public UserEntity signIn(LoginRequest dto) {
		return userRepository.findByNickname(dto.getNickname())
			.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}

	/**
	 * 유저 정보 수정
	 */
	public UserEntity modifyUser(UserModifyRequest dto) {
		UserEntity oldUser = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new RuntimeException("계정 정보가 존재하지 않습니다."));

		String saveNickname = oldUser.getNickname();
		ImageEntity saveImage = oldUser.getImage();

		// 닉네임 변경
		if (StringUtils.hasText(dto.getNickname()))
			saveNickname = dto.getNickname();

		// 프로필 사진 교체
		if (dto.getProfileImage() != null) {
			// 기존 이미지 삭제
			imageService.deleteImage(oldUser.getImage());
			// 이미지 저장
			saveImage = imageService.saveImage(dto.getProfileImage(), oldUser);
		}

		// 회원 정보 수정
		return userRepository.save(
			UserEntity.builder()
				.userId(oldUser.getUserId())
				.nickname(saveNickname)
				.image(saveImage)
				.build()
		);
	}

	/**
	 * 계정 삭제 처리
	 */
	public void deleteAccount(Long userId) {
		UserEntity oldUser = userRepository.getReferenceById(userId);
		// 프로필 이미지 존재하는 경우 삭제
		if (oldUser.getImage() != null)
			imageService.deleteImage(oldUser.getImage());
		// 계정 삭제
		userRepository.deleteById(userId);
	}
}
