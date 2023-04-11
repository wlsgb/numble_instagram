package com.instagram.numble_instagram.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.instagram.numble_instagram.model.entity.user.Follow;
import com.instagram.numble_instagram.model.entity.user.User;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Follow findByUserAndFollowUser(User user, User followUser);

	Follow findAllByUser(User user);

	long countAllByUser(User user);

	Follow findAllByFollowUser(User followUser);

	long countAllByFollowUser(User followUser);
}
