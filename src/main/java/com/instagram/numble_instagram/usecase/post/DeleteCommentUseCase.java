package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.CommentWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteCommentUseCase {
    private final UserService userService;
    private final CommentWriteService commentWriteService;

    @Transactional
    public void execute(Long userId, Long commentId) {
        User regUser = userService.getUser(userId);
        commentWriteService.delete(regUser, commentId);
    }
}
