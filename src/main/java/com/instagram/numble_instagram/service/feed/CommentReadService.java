package com.instagram.numble_instagram.service.feed;

import com.instagram.numble_instagram.exception.notFound.CommentNotFoundException;
import com.instagram.numble_instagram.model.entity.feed.Comment;
import com.instagram.numble_instagram.repository.feed.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentReadService {

    private final CommentRepository commentRepository;

    /**
     * 댓글 조회
     */
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
    }
}
