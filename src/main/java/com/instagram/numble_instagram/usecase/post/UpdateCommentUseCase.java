package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.feed.request.CommentModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.response.CommentResponse;
import com.instagram.numble_instagram.model.entity.feed.Comment;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.CommentWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateCommentUseCase {
    private final UserService userService;
    private final CommentWriteService commentWriteService;

    public CommentResponse execute(Long userId, CommentModifyRequest postModifyRequest) {
        User user = userService.getUser(userId);
        Comment modifiedComment = commentWriteService.modify(user, postModifyRequest.commentId(), postModifyRequest.content());
        return CommentResponse.convertResponse(modifiedComment);
    }
}
