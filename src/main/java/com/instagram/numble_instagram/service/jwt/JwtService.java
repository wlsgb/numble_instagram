package com.instagram.numble_instagram.service.jwt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.instagram.numble_instagram.config.jwt.JwtTokenProvider;
import com.instagram.numble_instagram.model.dto.jwt.Token;
import com.instagram.numble_instagram.model.entity.jwt.RefreshTokenEntity;
import com.instagram.numble_instagram.repository.jwt.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class JwtService {

	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshTokenRepository refreshTokenRepository;

	/**
	 * 로그인 처리
	 */
	@Transactional
	public void signIn(Token token) {
		// 저장할 refresh 토큰 생성
		RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
			.keyUserId(token.getKey())
			.refreshToken(token.getRefreshToken())
			.build();

		// key 값
		String userId = refreshToken.getKeyUserId();
		if (refreshTokenRepository.existsByKeyUserId(userId)) {
			log.debug("이미 존재하는 refresh 토큰 삭제 - [keyUserId : {}]", userId);
			// 이미 존재하는 refresh 토큰 삭제
			refreshTokenRepository.deleteAllByKeyUserId(userId);
			refreshTokenRepository.flush();
		}

		// refresh 토큰 저장
		refreshTokenRepository.save(refreshToken);
	}

	/**
	 * refresh 토큰 객체 조회
	 */
	public Optional<RefreshTokenEntity> getRefreshToken(String refreshToken) {
		return refreshTokenRepository.findByRefreshToken(refreshToken);
	}

	/**
	 * refresh 토큰 검증
	 */
	public Map<String, String> validateRefreshToken(String refreshToken) {
		RefreshTokenEntity refreshTokenEntity = getRefreshToken(refreshToken)
			.orElseThrow(IllegalArgumentException::new);

		String createAccessToken = jwtTokenProvider.validateRefreshToken(refreshTokenEntity);
		return createRefreshJson(createAccessToken);
	}

	/**
	 * 토큰 생성 여부에 따른 Map 생성
	 */
	public Map<String, String> createRefreshJson(String createdAccessToken) {
		Map<String, String> map = new HashMap<>();

		// Access Token 이 없는 경우
		if (!StringUtils.hasText(createdAccessToken)) {
			map.put("errorType", "Forbidden");
			map.put("status", "402");
			map.put("message", "Refresh 토큰이 만료되었습니다. 로그인이 필요합니다.");
			return map;
		}

		// 기존에 존재하는 accessToken 제거
		map.put("status", "200");
		map.put("message", "Refresh 토큰을 통한 Access Token 생성이 완료되었습니다.");
		map.put("accessToken", createdAccessToken);
		return map;
	}
}
