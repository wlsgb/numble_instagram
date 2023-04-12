package com.instagram.numble_instagram.model.dto.message.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MessageSendRequest(@NotNull Long toUserId, @NotBlank String content) {
}
