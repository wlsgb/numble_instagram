package com.instagram.numble_instagram.service.follow;

import com.instagram.numble_instagram.exception.notFound.FollowNotFoundException;
import com.instagram.numble_instagram.model.entity.follow.Follow;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.user.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FollowReadService {

	private final FollowRepository followRepository;

	/**
	 * 팔로우 정보 조회
	 */
	public Follow getFollow(User fromUser, User toUser) {
		return followRepository.findByFromUserAndToUser(fromUser, toUser)
				.orElseThrow(FollowNotFoundException::new);
	}

	/**
	 * 팔로잉 조회
	 */
	public long getFollowing(User fromUser) {
		return followRepository.countAllByFromUser(fromUser);
	}

	/**
	 * 팔로워 조회
	 */
	public long getFollower(User toUser) {
		return followRepository.countAllByToUser(toUser);
	}

}
