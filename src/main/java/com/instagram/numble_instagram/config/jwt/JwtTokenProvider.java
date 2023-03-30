package com.instagram.numble_instagram.config.jwt;

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
	private final long tokenValidTime;
	private final Key key;

	public JwtTokenProvider(
		@Value("${jwt.secret}") String secretKey,
		@Value("${jwt.token-valid-time}") long tokenValidTime
	) {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
		this.tokenValidTime = tokenValidTime;
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * JWT 토큰 생성
	 */
	public String createToken(UserEntity user) {
		Map<String, Object> payloads = new HashMap<>();
		payloads.put("userId", user.getUserId());
		payloads.put("nickname", user.getNickname());
		payloads.put("imageId", user.getImage().getImageId());
		Date now = new Date();
		log.info("Create Token");
		String token = Jwts.builder()
			.setSubject("JWT AUTH")
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(payloads) // 정보 저장
			.setIssuedAt(now) // 토큰 발행 시간 정보
			.setExpiration(new Date(now.getTime() + tokenValidTime)) // 만료시간 정보
			.signWith(key, SignatureAlgorithm.HS256)  // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
			.compact();
		log.debug("Create Token - token: {}", token);
		return token;
	}

}