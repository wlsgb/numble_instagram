package com.instagram.numble_instagram.config.jwt;

import com.instagram.numble_instagram.model.dto.jwt.Token;
import io.jsonwebtoken.Claims;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.instagram.numble_instagram.model.entity.user.UserEntity;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {
	private final long accessTokenValidTime;
	private final long refreshTokenValidTime;
	private final Key accessKey;
	private final Key refreshKey;

	public JwtTokenProvider(
		@Value("${jwt.access.secret}") String accessTokenSecretKey,
		@Value("${jwt.refresh.secret}") String refreshTokenSecretKey,
		@Value("${jwt.access.valid-time}") long accessTokenValidTime,
		@Value("${jwt.refresh.valid-time}") long refreshTokenValidTime
	) {
		accessTokenSecretKey = Base64.getEncoder().encodeToString(accessTokenSecretKey.getBytes());
		refreshTokenSecretKey = Base64.getEncoder().encodeToString(refreshTokenSecretKey.getBytes());
		this.accessTokenValidTime = accessTokenValidTime;
		this.refreshTokenValidTime = refreshTokenValidTime;
		this.accessKey = Keys.hmacShaKeyFor(accessTokenSecretKey.getBytes(StandardCharsets.UTF_8));
		this.refreshKey = Keys.hmacShaKeyFor(refreshTokenSecretKey.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * JWT 토큰 생성
	 */
	public Token createAccessToken(UserEntity user) {
		String primaryKey = String.valueOf(user.getUserId());
		Claims claims = Jwts.claims().setSubject(primaryKey);
		claims.put("nickname", user.getNickname());
		Date now = new Date();

		// Access Token
		String accessToken = Jwts.builder()
				.setClaims(claims) // 정보 저장
				.setIssuedAt(now) // 토큰 발행 시간 정보
				.setExpiration(new Date(now.getTime() + accessTokenValidTime)) // set Expiration
				// 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 셋팅
				.signWith(accessKey, SignatureAlgorithm.ES256)
				.compact();

		// Refresh Token
		String refreshToken = Jwts.builder()
				.setClaims(claims) // 정보 저장
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime() + refreshTokenValidTime))
				// 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 셋팅
				.signWith(refreshKey, SignatureAlgorithm.ES256)
				.compact();

		log.info("Create access token and refresh token");
		log.debug("Create Token - accessToken: {}, refreshToken: {}", accessToken, refreshToken);

		return Token.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.key(primaryKey)
				.build();
	}

}