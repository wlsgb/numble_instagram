package com.instagram.numble_instagram.model.dto.feed.request;

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
public class ReplySaveRequest {
	private Long userId;
	private Long commentId;
	private String content;
}
