package com.instagram.numble_instagram.config.security;

import com.instagram.numble_instagram.config.jwt.JwtAuthenticateFilter;
import com.instagram.numble_instagram.service.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtAuthenticateFilter jwtAuthenticateFilter;

  @Bean
  protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // csrf 보안 토큰 비활성화, JWT 토큰을 사용할경우 필수적으로 사용
    http.httpBasic().disable() // HTTP 기본 인증 비활성화
        .csrf().disable() // CSRF 보안 토큰 비활성화
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용 안함

    http.authorizeHttpRequests()
        .requestMatchers("/join", "/login").anonymous() // 인증되지 않은 사용자만 접근 허용
        .anyRequest().authenticated() // 그 외 요청은 인증 필요
        .and()
        // JWT 인증 필터 추가
        .addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}