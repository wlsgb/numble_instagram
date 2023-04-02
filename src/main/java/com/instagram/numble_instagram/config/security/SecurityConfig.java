package com.instagram.numble_instagram.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.instagram.numble_instagram.config.jwt.JwtAuthenticateFilter;
import com.instagram.numble_instagram.service.user.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final JwtAuthenticateFilter jwtAuthenticateFilter;

	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// csrf 보안 토큰 비활성화, JWT 토큰을 사용할경우 필수적으로 사용
		http.csrf().disable();
		http.httpBasic().disable();
		http.authorizeHttpRequests()
			.requestMatchers("/sign-in", "sign-up").permitAll()
			.anyRequest().authenticated();

		// Session 사용 안함 처리
		http.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// 필터 추가
		http.addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}