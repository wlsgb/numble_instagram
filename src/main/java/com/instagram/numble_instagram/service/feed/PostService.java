package com.instagram.numble_instagram.service.feed;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instagram.numble_instagram.model.dto.feed.request.PostModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.PostSaveRequest;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.model.entity.feed.PostEntity;
import com.instagram.numble_instagram.model.entity.image.ImageEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.feed.PostRepository;
import com.instagram.numble_instagram.repository.user.UserRepository;
import com.instagram.numble_instagram.service.image.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ImageService imageService;

	/**
	 * 글 저장
	 */
	@Transactional
	public PostResponse savePost(PostSaveRequest dto) {
		// 유저 정보 조회
		UserEntity regUser = userRepository.getReferenceById(dto.getUserId());
		// 이미지 저장
		ImageEntity saveImage = imageService.saveImage(dto.getImage(), regUser);
		// 글 저장
		PostEntity newPost = postRepository.save(
			PostEntity.builder()
				.content(dto.getContent())
				.regUser(regUser)
				.image(saveImage)
				.build()
		);
		return PostResponse.convertResponse(newPost);
	}

	/**
	 * 글 수정
	 */
	@Transactional
	public PostResponse modifyPost(PostModifyRequest dto) {
		// 기존에 작성된 글
		PostEntity oldPost = postRepository.getReferenceById(dto.getPostId());
		// 신규로 저장할 이미지가 존재하는 경우
		if (dto.getImage() != null) {
			// 이미지 삭제
			imageService.deleteImage(oldPost.getImage().getImageId());
		}
		// 등록 유저 정보 조회
		UserEntity regUser = oldPost.getRegUser();
		// 이미지 저장
		ImageEntity saveImage = imageService.saveImage(dto.getImage(), regUser);
		// 글 수정
		PostEntity modifiedPost = postRepository.save(
			PostEntity.builder()
				.postId(oldPost.getPostId())
				.image(saveImage)
				.build()
		);
		return PostResponse.convertResponse(modifiedPost);
	}

	/**
	 * 글 삭제
	 */
	@Transactional
	public void deletePost(Long postId) {
		// 기존에 작성된 글
		PostEntity oldPost = postRepository.getReferenceById(postId);
		// 이미지 삭제
		imageService.deleteImage(oldPost.getImage().getImageId());
		// 글 삭제
		postRepository.deleteById(postId);
	}
}
