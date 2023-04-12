package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.feed.request.PostModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.model.entity.feed.Post;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.PostWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdatePostUseCase {
    private final UserService userService;
    private final PostWriteService postWriteService;

    @Transactional
    public PostResponse execute(Long userId, PostModifyRequest postModifyRequest) {
        User user = userService.getUser(userId);
        Post modifiedPost = postWriteService.modify(user, postModifyRequest.postId(), postModifyRequest.content(), postModifyRequest.postImageFile());
        return PostResponse.convertResponse(modifiedPost);
    }
}
