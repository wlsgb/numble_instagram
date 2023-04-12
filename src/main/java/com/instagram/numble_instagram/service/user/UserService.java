package com.instagram.numble_instagram.service.user;

import com.instagram.numble_instagram.exception.notFound.UserNotFoundException;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;


    /**
     * 유저 ID로 회원 조회
     */
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * 닉네임으로 회원 조회
     */
    public User getUser(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(UserNotFoundException::new);
    }
}
