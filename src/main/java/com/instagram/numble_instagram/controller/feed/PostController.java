package com.instagram.numble_instagram.controller.feed;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.feed.request.PostModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.PostRegisterRequest;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.usecase.post.CreatePostUseCase;
import com.instagram.numble_instagram.usecase.post.DeletePostUseCase;
import com.instagram.numble_instagram.usecase.post.UpdatePostUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/feed/post")
public class PostController {

    private final CreatePostUseCase createPostUseCase;
    private final UpdatePostUseCase updatePostUseCase;
    private final DeletePostUseCase deletePostUseCase;

    /**
     * 글 생성
     */
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> savePost(
            @Valid PostRegisterRequest dto,
            @AuthenticationPrincipal SecurityUser user
    ) {
        return ResponseEntity.ok(createPostUseCase.execute(user.getUser().getUserId(), dto));
    }

    /**
     * 글 수정
     */
    @PutMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> modifyPost(
            @Valid PostModifyRequest dto,
            @AuthenticationPrincipal SecurityUser user
    ) {
        return ResponseEntity.ok(updatePostUseCase.execute(user.getUser().getUserId(), dto));
    }

    /**
     * 글 삭제
     */
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<HttpStatus> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal SecurityUser user
    ) {
        deletePostUseCase.execute(user.getUser().getUserId(), postId);
        return ResponseEntity.ok().build();
    }
}
