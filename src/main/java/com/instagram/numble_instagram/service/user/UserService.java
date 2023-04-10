package com.instagram.numble_instagram.service.user;

import com.instagram.numble_instagram.exception.notFound.UserNotFoundException;
import com.instagram.numble_instagram.model.dto.user.response.ProfileResponse;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.user.FollowRepository;
import com.instagram.numble_instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    /**
     * 프로필 조회
     */
    public ProfileResponse getProfile(Long userId) {
        // 프로필 조회할 타겟 유저 정보
        UserEntity targetUser = getUser(userId);
        // 프로필 정보
        return ProfileResponse.builder()
                .nickname(targetUser.getNickname())
                .profileImage(targetUser.getProfileImageUrl())
                .follower(followRepository.countAllByFollowUser(targetUser))
                .following(followRepository.countAllByUser(targetUser))
                .build();
    }

    /**
     * 유저 ID로 회원 조회
     */
    public UserEntity getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * 닉네임으로 회원 조회
     */
    public UserEntity getUser(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(UserNotFoundException::new);
    }
}
