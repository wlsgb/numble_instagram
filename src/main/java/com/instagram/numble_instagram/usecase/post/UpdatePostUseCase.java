package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.feed.request.PostModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.PostWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import com.instagram.numble_instagram.util.file.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdatePostUseCase {
    private final UserService userService;
    private final PostWriteService postWriteService;

    public PostResponse execute(Long userId, PostModifyRequest postModifyRequest) {
        User user = userService.getUser(userId);

        if (!FileUtil.existFile(postModifyRequest.postImageFile()))
            return postWriteService.modify(user, postModifyRequest.postId(), postModifyRequest.content(), null);

        return postWriteService.modify(user, postModifyRequest.postId(), postModifyRequest.content(), postModifyRequest.postImageFile());
    }
}
