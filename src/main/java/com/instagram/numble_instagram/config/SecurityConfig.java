package com.instagram.numble_instagram.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 활성화
@EnableMethodSecurity // @PreAuthorize 어노테이션 메소드 단위로 추가하기 위해 적용 (default : true)
public class SecurityConfig {
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// csrf 보안 토큰 비활성화, JWT 토큰을 사용할경우 필수적으로 사용
		http.csrf().disable();
		http.httpBasic().disable();
		http.authorizeHttpRequests().requestMatchers("/*").permitAll();
		return http.build();
	}
}