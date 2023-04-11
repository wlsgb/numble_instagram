package com.instagram.numble_instagram.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findById(Long.valueOf(userId))
			.orElseThrow(()-> new UsernameNotFoundException("회원 정보를 찾을 수 없습니다."));

		return new SecurityUser(user);
	}
}
