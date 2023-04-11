package com.instagram.numble_instagram.service.feed;

import com.instagram.numble_instagram.exception.invalidRequest.NotRegUserException;
import com.instagram.numble_instagram.exception.notFound.CommentNotFoundException;
import com.instagram.numble_instagram.model.entity.feed.Comment;
import com.instagram.numble_instagram.model.entity.feed.Post;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.feed.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommentWriteService {
	private final CommentRepository commentRepository;

	/**
	 * 댓글 저장
	 */
	public Comment register(User regUser, Post post, String content) {
		Comment newComment = Comment.register(regUser, post, content);
		post.addComment(newComment);
		return commentRepository.save(newComment);
	}

	/**
	 * 댓글 수정
	 */
	public Comment modify(User regUser, Long commentId, String content) {
		Comment comment = getComment(commentId);
		checkCommentRegUser(regUser, comment);
		comment.changeContent(content);
		return comment;
	}

	/**
	 * 댓글 삭제
	 */
	public void delete(User regUser, Long commentId) {
		Comment comment = getComment(commentId);
		checkCommentRegUser(regUser, comment);
		commentRepository.delete(comment);
	}

	/**
	 * 코멘트 조회
	 */
	private Comment getComment(Long commentId) {
		return commentRepository.findById(commentId)
				.orElseThrow(CommentNotFoundException::new);
	}

	/**
	 * 등록자 체크
	 */
	private static void checkCommentRegUser(User user, Comment comment) {
		if (!comment.isRegUser(user))
			throw new NotRegUserException();
	}
}
