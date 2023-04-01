package com.instagram.numble_instagram.model.dto.jwt;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshApiResponseMessage {

	public RefreshApiResponseMessage(Map<String, String> map) {
		this.status = map.get("status");
		this.message = map.get("message");
		this.errorType = map.get("errorType");
		this.accessToken = map.get("accessToken");
	}

	private String status;
	private String message;
	private String errorType;
	private String accessToken;
}
