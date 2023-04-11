package com.instagram.numble_instagram.service.feed;

import com.instagram.numble_instagram.exception.notFound.PostNotFoundException;
import com.instagram.numble_instagram.model.entity.feed.Post;
import com.instagram.numble_instagram.repository.feed.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostReadService {

    private final PostRepository postRepository;

    /**
     * 글 조회
     */
    public Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);
    }
}
