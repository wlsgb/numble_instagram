package com.instagram.numble_instagram.controller.feed;

import com.instagram.numble_instagram.model.dto.CursorResult;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.service.feed.PostReadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/feed")
public class FeedController {

	@Value("${service.paging.default-size}")
	private int PAGING_DEFAULT_SIZE;
	private final PostReadService postReadService;

	/**
	 * 피드 조회
	 */
	@GetMapping()
	public ResponseEntity<CursorResult<PostResponse>> getFeed(
		@RequestParam(required = false) Long cursorId
	) {
		CursorResult<PostResponse> postList = postReadService.getPostList(cursorId, PageRequest.of(0, PAGING_DEFAULT_SIZE));
		return ResponseEntity.ok(postList);
	}
}
