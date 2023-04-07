package com.instagram.numble_instagram.service.feed;

import org.springframework.stereotype.Service;

import com.instagram.numble_instagram.model.dto.feed.request.CommentModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.CommentSaveRequest;
import com.instagram.numble_instagram.model.dto.feed.response.CommentResponse;
import com.instagram.numble_instagram.model.entity.feed.CommentEntity;
import com.instagram.numble_instagram.model.entity.feed.PostEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.feed.CommentRepository;
import com.instagram.numble_instagram.repository.feed.PostRepository;
import com.instagram.numble_instagram.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	/**
	 * 댓글 저장
	 */
	public CommentResponse saveComment(CommentSaveRequest dto) {
		// 유저 정보 조회
		UserEntity regUser = userRepository.getReferenceById(dto.getUserId());
		// 글 정보 조회
		PostEntity post = postRepository.getReferenceById(dto.getPostId());
		// 댓글 저장
		CommentEntity newComment = commentRepository.save(
			CommentEntity.builder()
				.regUser(regUser)
				.post(post)
				.content(dto.getContent())
				.build()
		);
		return CommentResponse.convertResponse(newComment);
	}

	/**
	 * 댓글 수정
	 */
	public CommentResponse modifyComment(CommentModifyRequest dto) {
		// 기존에 작성한 댓글
		CommentEntity oldComment = commentRepository.getReferenceById(dto.getCommentId());
		// 댓글 수정
		CommentEntity modifiedComment = commentRepository.save(
			CommentEntity.builder()
				.commentId(oldComment.getCommentId())
				.content(dto.getContent())
				.build()
		);
		return CommentResponse.convertResponse(modifiedComment);
	}

	/**
	 * 댓글 삭제
	 */
	public void deleteComment(Long commentId) {
		// 기존에 작성한 댓글
		CommentEntity oldComment = commentRepository.getReferenceById(commentId);
		// 댓글 삭제
		commentRepository.delete(oldComment);
	}
}
