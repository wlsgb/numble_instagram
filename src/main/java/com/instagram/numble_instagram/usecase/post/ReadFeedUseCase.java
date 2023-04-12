package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.CursorResult;
import com.instagram.numble_instagram.model.dto.feed.response.PostResponse;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.FeedReadService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReadFeedUseCase {

    @Value("${service.paging.default-size}")
    private int PAGING_DEFAULT_SIZE;
    private final UserService userService;
    private final FeedReadService feedReadService;

    @Transactional(readOnly = true)
    public CursorResult<PostResponse> execute(Long userId, Long cursorId) {
        User user = userService.getUser(userId);
        return feedReadService.getFeedList(user, cursorId, PageRequest.of(0, PAGING_DEFAULT_SIZE));
    }
}
