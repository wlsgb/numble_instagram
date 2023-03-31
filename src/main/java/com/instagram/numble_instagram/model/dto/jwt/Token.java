package com.instagram.numble_instagram.model.dto.jwt;

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
public class Token {
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private String key;
}
