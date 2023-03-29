package com.instagram.numble_instagram.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.user.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	/**
	 * 닉네임 조회
	 */
	UserEntity findByNickname(String nickname);
}
