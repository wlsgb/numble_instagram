package com.instagram.numble_instagram.usecase.follow;

import com.instagram.numble_instagram.model.dto.follow.request.FollowRequest;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.follow.FollowWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteFollowUseCase {
    private final UserService userService;
    private final FollowWriteService followWriteService;

    @Transactional
    public void execute(Long fromUserId, FollowRequest followRequest) {
        User fromUser = userService.getUser(fromUserId);
        User toUser = userService.getUser(followRequest.toUserId());
        followWriteService.cancelFollow(fromUser, toUser);
    }
}
