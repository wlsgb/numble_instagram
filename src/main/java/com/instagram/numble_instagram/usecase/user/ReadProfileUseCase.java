package com.instagram.numble_instagram.usecase.user;

import com.instagram.numble_instagram.model.dto.user.response.ProfileResponse;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.follow.FollowReadService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadProfileUseCase {
    private final UserService userService;
    private final FollowReadService followReadService;

    public ProfileResponse execute(Long userId) {
        User user = userService.getUser(userId);
        long follower = followReadService.getFollower(user);
        long following = followReadService.getFollowing(user);
        return ProfileResponse.convertResponse(user, follower, following);
    }
}
