package com.instagram.numble_instagram.service.feed;

import com.instagram.numble_instagram.exception.notFound.ReplyNotFoundException;
import com.instagram.numble_instagram.model.entity.feed.Reply;
import com.instagram.numble_instagram.repository.feed.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ReplyReadService {

    private final ReplyRepository replyRepository;

    /**
     * 댓글 조회
     */
    public Reply getReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(ReplyNotFoundException::new);
    }
}
