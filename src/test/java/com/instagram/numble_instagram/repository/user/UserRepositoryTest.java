package com.instagram.numble_instagram.repository.user;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.image.ImageRepository;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class UserRepositoryTest {

	@Autowired
	private ImageRepository imageRepository;
	@Autowired
	private UserRepository userRepository;

	@Test
	void signupTest() {
		String nickname = "테스트 닉네임";

		UserEntity user = UserEntity.builder()
			.nickname(nickname)
			.build();

		userRepository.save(user);

		List<UserEntity> userList = userRepository.findAll();

		System.out.println(userList);
	}
}
