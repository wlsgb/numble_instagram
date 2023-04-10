package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.service.feed.PostWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeletePostUseCase {
    private final UserService userService;
    private final PostWriteService postWriteService;

    public void execute(Long userId, Long postId) {
        UserEntity regUser = userService.getUser(userId);
        postWriteService.delete(regUser, postId);
    }
}
