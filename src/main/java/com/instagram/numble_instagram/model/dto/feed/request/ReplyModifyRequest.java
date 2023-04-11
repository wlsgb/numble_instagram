package com.instagram.numble_instagram.model.dto.feed.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReplyModifyRequest(@NotNull Long replyId, @NotBlank String content) {
}
