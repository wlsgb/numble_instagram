package com.instagram.numble_instagram.service.feed;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.instagram.numble_instagram.model.dto.CursorResult;
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
	 * 글 목록 조회
	 */
	@Transactional
	public CursorResult<PostResponse> getPostList(Long cursorId, Pageable page) {
		// 글 목록 조회
		final List<PostEntity> postList = getPagingPostList(cursorId, page);
		// 마지막 글 아이디
		final Long lastPostIdOfList = postList.isEmpty() ?
			null : postList.get(postList.size() - 1).getPostId();

		return CursorResult.<PostResponse>builder()
			.list(postList.stream()
				.map(PostResponse::convertResponse)
				.toList())
			.hasNext(hasNext(lastPostIdOfList))
			.build();
	}

	/**
	 * 페이징 된 글 목록 조회
	 */
	private List<PostEntity> getPagingPostList(Long postId, Pageable page) {
		return postId == null ?
			postRepository.findAllByOrderByPostIdDesc(page) :
			postRepository.findByPostIdLessThanOrderByPostIdDesc(postId, page);
	}

	/**
	 * 다음 글이 존재하는지 여부
	 */
	private Boolean hasNext(Long postId) {
		if (postId == null)
			return false;
		return postRepository.existsByPostIdLessThan(postId);
	}

	/**
	 * 글 저장
	 */
	@Transactional
	public PostResponse savePost(PostSaveRequest dto) {
		// 유저 정보 조회
		UserEntity regUser = userRepository.getReferenceById(dto.getUserId());
		ImageEntity saveImage = null;
		// 이미지가 존재하는 경우
		if (dto.getImage() != null) {
			// 이미지 저장
			saveImage = imageService.saveImage(dto.getImage(), regUser);
		}
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
		// 이미지를 변경하지 않는 경우
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
