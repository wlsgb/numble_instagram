package com.instagram.numble_instagram.model.dto.feed.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentModifyRequest(@NotNull Long commentId, @NotBlank String content) {
}
