package com.instagram.numble_instagram.controller.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.instagram.numble_instagram.model.entity.user.UserEntity;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest
class AuthControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.build();
	}

	@Test
	@DisplayName("로그인 jwt 테스트")
	public void signInTokenTest() throws Exception {
		String body = new ObjectMapper().writeValueAsString(
			UserEntity.builder()
				.nickname("테스트 계정")
				.build()
		);

		mvc.perform(post("/sign-in")
				// Mockmvc에 바디 데이터 추가
				.content(body)
				// 받을 데이터 타입 설정 --> JSON으로 받기 때문에 해당 설정 ON
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk());
	}
}