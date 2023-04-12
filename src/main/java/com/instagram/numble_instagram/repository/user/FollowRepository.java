package com.instagram.numble_instagram.repository.user;

import com.instagram.numble_instagram.model.entity.follow.Follow;
import com.instagram.numble_instagram.model.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

	Optional<Follow> findByFromUserAndToUser(User fromUser, User toUser);

	List<Follow> findAllByFromUser(User fromUser);

	long countAllByFromUser(User fromUser);

	List<Follow> findAllByToUser(User toUser);

	long countAllByToUser(User toUser);
}
