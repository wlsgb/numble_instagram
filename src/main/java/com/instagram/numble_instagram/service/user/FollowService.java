package com.instagram.numble_instagram.service.user;

import org.springframework.stereotype.Service;

import com.instagram.numble_instagram.model.dto.user.request.FollowRequest;
import com.instagram.numble_instagram.model.entity.user.FollowEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.user.FollowRepository;
import com.instagram.numble_instagram.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class FollowService {
	private UserRepository userRepository;
	private FollowRepository followRepository;

	/**
	 * 유저 팔로우
	 */
	public void followUser(FollowRequest dto) {
		// 본인 정보
		UserEntity user = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
		// 팔로우할 대상자 정보
		UserEntity followUser = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("팔로우할 대상 회원 정보가 존재하지 않습니다."));
		// 팔로우 요청
		followRepository.save(
			FollowEntity.builder()
				.user(user)
				.followUser(followUser)
				.build()
		);
	}

	/**
	 * 팔로우 취소
	 */
	public void followUserCancel(FollowRequest dto) {
		// 본인 정보
		UserEntity user = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("회원 정보가 존재하지 않습니다."));
		// 팔로우할 대상자 정보
		UserEntity followUser = userRepository.findById(dto.getUserId())
			.orElseThrow(() -> new IllegalArgumentException("팔로우할 대상 회원 정보가 존재하지 않습니다."));
		// 기존 팔로우 정보
		FollowEntity olfFollow = followRepository.findByUserAndFollowUser(user, followUser);
		// 팔로우 취소 (삭제)
		followRepository.delete(olfFollow);

	}
}
