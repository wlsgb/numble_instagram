package com.instagram.numble_instagram.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.instagram.numble_instagram.service.user.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		log.debug("JwtAuthenticateFilter 호출");

		String authorization = request.getHeader("Authorization");
		String token = "";
		String userId = "";

		if (StringUtils.hasText(authorization)) {
			token = jwtTokenProvider.removeBearerByToken(authorization);
			userId = jwtTokenProvider.getUserIdByAccessToken(token);
		}

		// 현재 SecurityContextHolder 에 인증객체가 있는지 확인
		if (StringUtils.hasText(userId) && SecurityContextHolder.getContext().getAuthentication() == null) {
			log.info("jwt filter = {}", userId);
			UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

			// 토큰 유효여부 확인
			log.info("JWT Filter token = {}", token);
			log.info("JWT Filter userDetails = {}", userDetails.getUsername());
			if (jwtTokenProvider.isValidAccessToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
					= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request,response);
	}
}
