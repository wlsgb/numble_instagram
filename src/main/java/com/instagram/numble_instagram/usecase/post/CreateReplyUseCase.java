package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.feed.request.ReplyRegisterRequest;
import com.instagram.numble_instagram.model.dto.feed.response.ReplyResponse;
import com.instagram.numble_instagram.model.entity.feed.Comment;
import com.instagram.numble_instagram.model.entity.feed.Reply;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.CommentReadService;
import com.instagram.numble_instagram.service.feed.ReplyWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateReplyUseCase {
    private final UserService userService;
    private final CommentReadService commentReadService;
    private final ReplyWriteService replyWriteService;

    public ReplyResponse execute(Long userId, ReplyRegisterRequest replyRegisterRequest) {
        User user = userService.getUser(userId);
        Comment comment = commentReadService.getComment(replyRegisterRequest.commentId());
        Reply newReply = replyWriteService.register(user, comment, replyRegisterRequest.content());
        return ReplyResponse.convertResponse(newReply);
    }
}
