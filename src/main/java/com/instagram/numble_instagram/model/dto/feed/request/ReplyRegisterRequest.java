package com.instagram.numble_instagram.model.dto.feed.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReplyRegisterRequest(@NotNull Long commentId, @NotBlank String content) {
}
