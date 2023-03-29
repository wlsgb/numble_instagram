package com.instagram.numble_instagram.repository.user;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.instagram.numble_instagram.model.entity.user.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("회원 가입 테스트")
	void signupTest() {
		// given
		UserEntity newUser = UserEntity.builder()
			.nickname("유저 생성 테스트")
			.build();

		userRepository.save(newUser);

		Optional<UserEntity> selectedUser = userRepository.findById(newUser.getUserId());

		assertThat(selectedUser).isNotEmpty();

		log.info("selectedUser: {}", selectedUser);
	}

	@Test
	@DisplayName("모든 회원 조회 테스트")
	void findAllTest() {
		//given
		UserEntity newUser = UserEntity.builder()
			.nickname("모든 회원 조회 테스트")
			.build();

		userRepository.save(newUser);

		List<UserEntity> userList = userRepository.findAll();

		//when
		userList = userList.stream()
			.filter(u -> u.getNickname().contains("테스트"))
			.collect(Collectors.toList());

		//then
		assertThat(userList)
			.isNotEmpty();
	}

	@Test
	@DisplayName("유저 플래그 삭제 처리 테스트")
	void deleteTest() {
		// given
		UserEntity newUser = UserEntity.builder()
			.nickname("삭제될 유저")
			.build();
		userRepository.save(newUser);

		Optional<UserEntity> optiUser = userRepository.findById(newUser.getUserId());
		optiUser.ifPresent(userEntity -> userRepository.delete(userEntity));

		Optional<UserEntity> user = userRepository.findById(optiUser.get().getUserId());

		assertThat(user)
			.isEmpty();

		log.info("user: {}", user);
	}
}
