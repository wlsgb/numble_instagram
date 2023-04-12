package com.instagram.numble_instagram.controller.follow;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.follow.request.FollowRequest;
import com.instagram.numble_instagram.usecase.follow.CreateFollowUseCase;
import com.instagram.numble_instagram.usecase.follow.DeleteFollowUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/follow")
public class FollowController {
    private final CreateFollowUseCase createFollowUseCase;
    private final DeleteFollowUseCase deleteFollowUseCase;

    /**
     * 팔로우 요청
     */
    @PostMapping(value = "")
    public ResponseEntity<HttpStatus> followUser(
            @Validated @RequestBody FollowRequest followRequest,
            @AuthenticationPrincipal SecurityUser user
    ) {
        createFollowUseCase.execute(user.getUser().getUserId(), followRequest);
        return ResponseEntity.ok().build();
    }

    /**
     * 팔로우 취소
     */
    @DeleteMapping(value = "")
    public ResponseEntity<HttpStatus> cancelFollow(
            @Validated @RequestBody FollowRequest followRequest,
            @AuthenticationPrincipal SecurityUser user
    ) {
        deleteFollowUseCase.execute(user.getUser().getUserId(), followRequest);
        return ResponseEntity.ok().build();
    }
}
