package com.instagram.numble_instagram.service.feed;

import com.instagram.numble_instagram.exception.invalidRequest.NotRegUserException;
import com.instagram.numble_instagram.exception.notFound.ReplyNotFoundException;
import com.instagram.numble_instagram.model.entity.feed.Comment;
import com.instagram.numble_instagram.model.entity.feed.Reply;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.feed.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReplyWriteService {
	private final ReplyRepository replyRepository;

	/**
	 * 답글 저장
	 */
	public Reply register(User regUser, Comment comment, String content) {
		Reply newReply = Reply.register(regUser, comment, content);
		comment.addReply(newReply);
		return replyRepository.save(newReply);
	}

	/**
	 * 답글 수정
	 */
	public Reply modify(User user, Long replyId, String content) {
		Reply reply = getReply(replyId);
		checkReplyRegUser(user, reply);
		reply.changeContent(content);
		return reply;
	}

	/**
	 * 답글 삭제
	 */
	public void delete(User user, Long replyId) {
		Reply reply = getReply(replyId);
		checkReplyRegUser(user, reply);
		replyRepository.delete(reply);
	}

	/**
	 * 댓글 조회
	 */
	public Reply getReply(Long replyId) {
		return replyRepository.findById(replyId)
				.orElseThrow(ReplyNotFoundException::new);
	}

	/**
	 * 등록자 체크
	 */
	private static void checkReplyRegUser(User user, Reply reply) {
		if (!reply.isRegUser(user))
			throw new NotRegUserException();
	}
}
