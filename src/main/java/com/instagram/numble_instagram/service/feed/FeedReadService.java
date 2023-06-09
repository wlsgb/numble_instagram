package com.instagram.numble_instagram.service.feed;

import com.instagram.numble_instagram.model.dto.CursorResult;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.model.entity.feed.Post;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.feed.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FeedReadService {
    private final PostRepository postRepository;

    /**
     * 글 목록 조회
     */
    public CursorResult<PostResponse> getFeedList(User user, Long cursorId, Pageable page) {
        // 글 목록 조회
        final List<Post> postList = getPagingPostList(user, cursorId, page);
        // 마지막 글 아이디
        final Long lastPostIdOfList = postList.isEmpty() ?
                null : postList.get(postList.size() - 1).getPostId();

        return CursorResult.<PostResponse>builder()
                .list(postList.stream()
                        .map(PostResponse::convertResponse)
                        .toList())
                .hasNext(hasNext(user, lastPostIdOfList))
                .build();
    }

    /**
     * 페이징 된 글 목록 조회
     */
    private List<Post> getPagingPostList(User regUser, Long postId, Pageable page) {
        return postId == null ?
                postRepository.findAllByRegUserOrderByPostIdDesc(regUser, page) :
                postRepository.findByRegUserAndPostIdLessThanOrderByPostIdDesc(regUser, postId, page);
    }

    /**
     * 다음 글이 존재하는지 여부
     */
    private Boolean hasNext(User regUser, Long postId) {
        if (postId == null)
            return false;
        return postRepository.existsByRegUserAndPostIdLessThan(regUser, postId);
    }
}
