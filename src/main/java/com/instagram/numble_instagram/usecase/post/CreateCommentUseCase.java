package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.feed.request.CommentRegisterRequest;
import com.instagram.numble_instagram.model.dto.feed.response.CommentResponse;
import com.instagram.numble_instagram.model.entity.feed.Comment;
import com.instagram.numble_instagram.model.entity.feed.Post;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.CommentWriteService;
import com.instagram.numble_instagram.service.feed.PostReadService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreateCommentUseCase {
    private final UserService userService;
    private final PostReadService postReadService;
    private final CommentWriteService commentWriteService;

    @Transactional
    public CommentResponse execute(Long userId, CommentRegisterRequest commentRegisterRequest) {
        User user = userService.getUser(userId);
        Post post = postReadService.getPost(commentRegisterRequest.postId());
        Comment newComment = commentWriteService.register(user, post, commentRegisterRequest.content());
        return CommentResponse.convertResponse(newComment);
    }
}
