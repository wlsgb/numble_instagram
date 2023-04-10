package com.instagram.numble_instagram.model.dto.feed.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record PostRegisterRequest(@NotBlank String content, MultipartFile postImageFile) {
}
