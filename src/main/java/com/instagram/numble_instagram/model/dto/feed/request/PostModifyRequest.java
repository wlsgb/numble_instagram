package com.instagram.numble_instagram.model.dto.feed.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record PostModifyRequest(@NotNull Long postId, @NotBlank String content, MultipartFile postImageFile) {
}
