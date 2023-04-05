package com.instagram.numble_instagram.model.dto.feed.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostSaveRequest {
	private Long userId;
	private String content;
	private MultipartFile image;
}