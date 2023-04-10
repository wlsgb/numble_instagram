package com.instagram.numble_instagram.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.numble_instagram.model.entity.user.FollowEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

	FollowEntity findByUserAndFollowUser(UserEntity user, UserEntity followUser);

	FollowEntity findAllByUser(UserEntity user);

	long countAllByUser(UserEntity user);

	FollowEntity findAllByFollowUser(UserEntity followUser);

	long countAllByFollowUser(UserEntity followUser);
}
