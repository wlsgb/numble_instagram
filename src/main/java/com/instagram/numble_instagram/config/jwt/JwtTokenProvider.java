package com.instagram.numble_instagram.config.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.instagram.numble_instagram.model.dto.jwt.Token;
import com.instagram.numble_instagram.model.entity.jwt.RefreshTokenEntity;

import io.jsonwebtoken.Claims;
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
	 * 토큰 생성
	 */
	private String generateToken(String userId, String nickname, long tokenValidTime, Key secret) {
		Claims claims = Jwts.claims()
			.setSubject(userId);
		claims.put("nickname", nickname);
		Date now = new Date();
		// Access Token
		return Jwts.builder()
			.setIssuer("numble_instagram")
			.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
			.setClaims(claims) // 정보 저장
			.setIssuedAt(now) // 토큰 발행 시간 정보
			.setExpiration(new Date(now.getTime() + tokenValidTime)) // set Expiration
			// 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 셋팅
			.signWith(secret, SignatureAlgorithm.HS256)
			.compact();
	}

	/**
	 * Access 토큰 생성
	 */
	public String createAccessToken(String userId, String nickname) {
		return generateToken(userId, nickname, accessTokenValidTime, accessKey);
	}

	/**
	 * Refresh 토큰 생성
	 */
	public String createRefreshToken(String userId, String nickname) {
		return generateToken(userId, nickname, refreshTokenValidTime, refreshKey);
	}

	/**
	 * JWT 토큰 그룹 생성
	 */
	public Token createToken(String userId, String nickname) {
		// Access Token
		String accessToken = createAccessToken(userId, nickname);

		// Refresh Token
		String refreshToken = createRefreshToken(userId, nickname);

		log.info("Create accessToken and refreshToken");
		log.debug("Create Token - accessToken: {}, refreshToken: {}", accessToken, refreshToken);

		return Token.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.key(getUserIdByAccessToken(accessToken))
			.build();
	}

	/**
	 * 토큰 파싱
	 */
	private Claims parseToken(String token, Key secret) {
		token = removeBearerByToken(token);
		return Jwts.parserBuilder()
			.setSigningKey(secret)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	/**
	 * Access 토큰 파싱
	 */
	public Claims parseAccessToken(String token) {
		return parseToken(token, accessKey);
	}

	/**
	 * Refresh 토큰 파싱
	 */
	public Claims parseRefreshToken(String token) {
		return parseToken(token, refreshKey);
	}

	/**
	 * 토큰 만료기한 조회
	 */
	private Date getExpirationDate(String token, Key secret) {
		return parseToken(token, secret).getExpiration();
	}

	/**
	 * Access 토큰 만료기한 조회
	 */
	public Date getExpirationDateByAccessToken(String token) {
		return getExpirationDate(token, accessKey);
	}

	/**
	 * Refresh 토큰 만료기한 조회
	 */
	public Date getExpirationDateByRefreshToken(String token) {
		return getExpirationDate(token, refreshKey);
	}

	/**
	 * 토큰이 만료 여부
	 */
	private boolean isTokenExpired(String token, Key secret) {
		return getExpirationDate(token, secret).before(new Date());
	}

	/**
	 * Access 토큰 만료 여부
	 */
	public boolean isAccessTokenExpired(String token) {
		return isTokenExpired(token, accessKey);
	}

	/**
	 * Refresh 토큰 만료 여부
	 */
	public boolean isRefreshTokenExpired(String token) {
		return isTokenExpired(token, refreshKey);
	}

	/**
	 * 토큰 앞 부분('Bearer') 제거 메소드
	 */
	public String removeBearerByToken(String token) {
		if (!token.startsWith("Bearer "))
			return token;
		return token.substring("Bearer ".length());
	}

	/**
	 * 토큰에 저장된 유저 ID 조회
	 */
	private String getUserIdByToken(String token, Key secret) {
		Claims claims = parseToken(token, secret);
		return claims.getSubject();
	}

	/**
	 * Access 토큰에 저장된 유저 ID 조회
	 */
	public String getUserIdByAccessToken(String token) {
		return getUserIdByToken(token, accessKey);
	}

	/**
	 * Refresh 토큰에 저장된 유저 ID 조회
	 */
	public String getUserIdByRefreshToken(String token) {
		return getUserIdByToken(token, refreshKey);
	}

	/**
	 * 토큰에 저장된 유저 ID 조회
	 */
	private String getNicknameByToken(String token, Key secret) {
		Claims claims = parseToken(token, secret);
		return claims.get("nickname").toString();
	}

	/**
	 * Access 토큰에 저장된 유저 ID 조회
	 */
	public String getNicknameByAccessToken(String token) {
		return getNicknameByToken(token, accessKey);
	}

	/**
	 * Refresh 토큰에 저장된 유저 ID 조회
	 */
	public String getNicknameRefreshToken(String token) {
		return getNicknameByToken(token, refreshKey);
	}

	/**
	 * 토큰 유효성 검사
	 */
	private boolean isValidToken(String token, Key secret, UserDetails userDetails) {
		String userId = getUserIdByToken(token, secret);
		return (userId.equals(userDetails.getUsername())) && !isTokenExpired(token, secret);
	}

	/**
	 * Access 토큰 유효성 검사
	 */
	public boolean isValidAccessToken(String token, UserDetails userDetails){
		return isValidToken(token, accessKey, userDetails);
	}

	/**
	 * Refresh 토큰 유효성 검사
	 */
	public boolean isValidRefreshToken(String token, UserDetails userDetails){
		return isValidToken(token, refreshKey, userDetails);
	}

	/**
	 * Refresh Token 유효성 검증 후
	 * 문제 없다면 새로운 Access Token 생성하여 리턴
	 */
	public String validateRefreshToken(RefreshTokenEntity refreshTokenEntity) {
		// refresh 객체에서 refreshToken 추출
		String refreshToken = refreshTokenEntity.getRefreshToken();

		try {
			// refresh 토큰의 만료 시간이 지나지 않았을 경우, 새로운 access 토큰을 생성
			if (!isRefreshTokenExpired(refreshToken))
				return createAccessToken(
					getUserIdByRefreshToken(refreshToken),
					getNicknameRefreshToken(refreshToken)
				);

		} catch (Exception e) {
			// refresh 토큰이 만료된 경우, 로그인 필요
			return null;
		}
		return null;
	}


}