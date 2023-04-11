package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.feed.request.PostRegisterRequest;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.PostWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import com.instagram.numble_instagram.util.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CreatePostUseCase {
    private final UserService userService;
    private final PostWriteService postWriteService;
    private final FileStore imageFileStore;

    public PostResponse execute(Long regUserId, PostRegisterRequest postRegisterRequest) {
        User regUser = userService.getUser(regUserId);
        String postImageUrl = imageFileStore.uploadFile(postRegisterRequest.postImageFile());
        return postWriteService.register(postRegisterRequest.content(), postImageUrl, regUser);
    }
}
