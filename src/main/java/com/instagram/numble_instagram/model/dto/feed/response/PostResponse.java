package com.instagram.numble_instagram.model.dto.feed.response;

import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.feed.PostEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostResponse {
    private Long postId;
    private String content;
    private String imageUrl;
    private UserResponse regUser;
    private LocalDateTime regDate;
    private LocalDateTime updDate;
    private List<CommentResponse> commentList;

    public static PostResponse convertResponse(PostEntity post) {
        if (post == null)
            return new PostResponse();

        return PostResponse.builder()
                .postId(post.getPostId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .regUser(UserResponse.convertResponse(post.getRegUser()))
                .regDate(post.getRegDate())
                .updDate(post.getUpdDate())
                .commentList(
                        post.getCommentList() == null ?
                                Collections.emptyList() :
                                post.getCommentList().stream()
                                        .map(CommentResponse::convertResponse)
                                        .toList()
                )
                .build();
    }
}
