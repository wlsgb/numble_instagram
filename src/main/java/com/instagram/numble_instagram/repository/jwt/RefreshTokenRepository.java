package com.instagram.numble_instagram.repository.jwt;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.instagram.numble_instagram.model.entity.jwt.RefreshTokenEntity;


@Repository
@Transactional
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

	/**
	 * 토큰 조회
	 */
	Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);

	/**
	 * 데이터 존재여부 확인
	 */
	boolean existsByKeyUserId(String keyUserId);

	/**
	 * Refresh 토큰 존재 여부
	 */
	boolean existsByRefreshToken(String refreshToken);

	/**
	 * 리프레시 토큰 삭제
	 */
	@Transactional
	void deleteAllByKeyUserId(String keyUserId);

	/**
	 * 리프레시 토큰 삭제
	 */
	@Transactional
	void deleteByRefreshToken(String refreshToken);
}
