package com.instagram.numble_instagram.usecase.post;

import com.instagram.numble_instagram.model.dto.feed.request.ReplyModifyRequest;
import com.instagram.numble_instagram.model.dto.feed.response.ReplyResponse;
import com.instagram.numble_instagram.model.entity.feed.Reply;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.service.feed.ReplyWriteService;
import com.instagram.numble_instagram.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateReplyUseCase {
    private final UserService userService;
    private final ReplyWriteService replyWriteService;

    public ReplyResponse execute(Long userId, ReplyModifyRequest replyModifyRequest) {
        User user = userService.getUser(userId);
        Reply modifiedReply = replyWriteService.modify(user, replyModifyRequest.replyId(), replyModifyRequest.content());
        return ReplyResponse.convertResponse(modifiedReply);
    }
}
