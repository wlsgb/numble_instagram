package com.instagram.numble_instagram.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * 닉네임 조회
	 */
	Optional<UserEntity> findByNickname(String nickname);

	/**
	 * 닉네임 존재 여부
	 */
	boolean existsByNickname(String nickname);
}
