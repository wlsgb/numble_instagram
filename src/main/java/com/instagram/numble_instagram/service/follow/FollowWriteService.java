package com.instagram.numble_instagram.service.follow;

import com.instagram.numble_instagram.exception.invalidRequest.NotFromUserException;
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
@Transactional
public class FollowWriteService {

	private final FollowRepository followRepository;

	/**
	 * 유저 팔로우
	 */
	public void followUser(User fromUser, User toUser) {
		Follow newFollow = Follow.followUser(fromUser, toUser);
		try {
			getFollow(fromUser, toUser);
		} catch (FollowNotFoundException e) {
			followRepository.save(newFollow);
		}
	}

	/**
	 * 팔로우 취소
	 */
	public void cancelFollow(User fromUser, User toUser) {
		Follow follow = getFollow(fromUser, toUser);
		followRepository.delete(follow);
	}

	/**
	 * 팔로우 정보 조회
	 */
	private Follow getFollow(User fromUser, User toUser) {
		return followRepository.findByFromUserAndToUser(fromUser, toUser)
				.orElseThrow(FollowNotFoundException::new);
	}

	/**
	 * 팔로우 요청자 검증
	 */
	private void checkFromUser(User fromUser, Follow follow) {
		if (!follow.isFromUser(fromUser))
			throw new NotFromUserException();
	}
}
