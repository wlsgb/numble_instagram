package com.instagram.numble_instagram.controller.feed;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.feed.request.PostModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.PostSaveRequest;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.service.feed.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/feed/post")
public class PostController {

	private final PostService postService;

	/**
	 * 글 생성
	 */
	@PostMapping()
	public ResponseEntity<PostResponse> savePost(
		@RequestBody PostSaveRequest dto,
		@AuthenticationPrincipal SecurityUser user
	) {
		dto.setUserId(user.getUser().getUserId());
		return ResponseEntity.ok(postService.savePost(dto));
	}

	/**
	 * 글 수정
	 */
	@PutMapping()
	public ResponseEntity<PostResponse> modifyPost(
		@RequestBody PostModifyRequest dto
	) {
		return ResponseEntity.ok(postService.modifyPost(dto));
	}

	/**
	 * 글 삭제
	 */
	@DeleteMapping()
	public ResponseEntity<HttpStatus> deletePost(
		@RequestBody Long postId
	) {
		postService.deletePost(postId);
		return ResponseEntity.ok().build();
	}
}
