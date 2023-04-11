package com.instagram.numble_instagram.controller.feed;

import com.instagram.numble_instagram.model.dto.CursorResult;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.usecase.post.ReadFeedUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/feed")
public class FeedController {

    private final ReadFeedUseCase readFeedUseCase;

    /**
     * 피드 조회
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<CursorResult<PostResponse>> getFeed(
            @RequestParam(required = false) Long cursorId,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(readFeedUseCase.execute(userId, cursorId));
    }
}
