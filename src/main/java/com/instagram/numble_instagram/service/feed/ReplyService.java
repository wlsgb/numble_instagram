package com.instagram.numble_instagram.service.feed;

import org.springframework.stereotype.Service;

import com.instagram.numble_instagram.model.dto.feed.request.ReplyModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.request.ReplySaveRequest;
import com.instagram.numble_instagram.model.dto.feed.response.ReplyResponse;
import com.instagram.numble_instagram.model.entity.feed.CommentEntity;
import com.instagram.numble_instagram.model.entity.feed.ReplyEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.feed.CommentRepository;
import com.instagram.numble_instagram.repository.feed.ReplyRepository;
import com.instagram.numble_instagram.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReplyService {
	private final UserRepository userRepository;
	private final CommentRepository commentRepository;
	private final ReplyRepository replyRepository;

	/**
	 * 답글 저장
	 */
	public ReplyResponse saveReply(ReplySaveRequest dto) {
		// 유저 정보 조회
		UserEntity regUser = userRepository.getReferenceById(dto.getUserId());
		// 글 정보 조회
		CommentEntity comment = commentRepository.getReferenceById(dto.getCommentId());
		// 답글 저장
		ReplyEntity newReply = replyRepository.save(
			ReplyEntity.builder()
				.regUser(regUser)
				.comment(comment)
				.content(dto.getContent())
				.build()
		);
		return ReplyResponse.convertResponse(newReply);
	}

	/**
	 * 답글 수정
	 */
	public ReplyResponse modifyReply(ReplyModifyRequest dto) {
		// 기존에 작성한 답글
		ReplyEntity oldReply = replyRepository.getReferenceById(dto.getReplyId());
		// 답글 수정
		ReplyEntity modifiedReply = replyRepository.save(
			ReplyEntity.builder()
				.replyId(oldReply.getReplyId())
				.content(dto.getContent())
				.build()
		);
		return ReplyResponse.convertResponse(modifiedReply);
	}

	/**
	 * 답글 삭제
	 */
	public void deleteReply(Long replyId) {
		// 기존에 작성한 답글
		ReplyEntity oldReply = replyRepository.getReferenceById(replyId);
		// 답글 삭제
		replyRepository.delete(oldReply);
	}
}
