package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.ReplyWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteReplyUseCase {
    private final UserService userService;
    private final ReplyWriteService replyWriteService;

    public void execute(Long userId, Long replyId) {
        User regUser = userService.getUser(userId);
        replyWriteService.delete(regUser, replyId);
    }
}
