package com.instagram.numble_instagram.service.user;

import com.instagram.numble_instagram.exception.notFound.UserNotFoundException;
import com.instagram.numble_instagram.model.dto.user.request.LoginRequest;
import com.instagram.numble_instagram.model.dto.user.request.UserJoinRequest;
import com.instagram.numble_instagram.model.dto.user.request.UserModifyRequest;
import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.model.entity.user.User;
import com.instagram.numble_instagram.repository.user.UserRepository;
import com.instagram.numble_instagram.util.file.ImageFileStoreImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final ImageFileStoreImpl imageFileStore;

    /**
     * 회원가입
     */
    public UserResponse join(UserJoinRequest userJoinRequest) {
        validateDuplicateNickname(userJoinRequest.getNickname());
        String profileImageUrl = imageFileStore.uploadFile(userJoinRequest.getProfileImage());
        User newUser = User.join(userJoinRequest.getNickname(), profileImageUrl);
        // 회원 저장
        userRepository.save(newUser);
        return UserResponse.convertResponse(newUser);
    }

    /**
     * 로그인
     */
    public UserResponse login(LoginRequest dto) {
        User loginUser = userRepository.findByNickname(dto.getNickname())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return UserResponse.convertResponse(loginUser);
    }

    /**
     * 회원 정보 수정
     */
    public UserResponse modify(UserModifyRequest dto) {
        // 기존 회원 정보
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        checkNicknameAndChange(dto.getNickname(), user);
        checkImageFileAndChange(dto.getProfileImage(), user);
        return UserResponse.convertResponse(user);
    }

    /**
     * 계정 삭제 처리
     */
    public void delete(Long userId) {
        User user = userRepository.getReferenceById(userId);
        imageFileStore.deleteFile(user.getProfileImageUrl());
        userRepository.deleteById(userId);
    }

    /**
     * 닉네임 체크 후 변경
     */
    private void checkNicknameAndChange(String nickname, User user) {
        if (StringUtils.hasText(nickname) && !user.getNickname().equals(nickname))
            validateDuplicateNickname(nickname);
        if (StringUtils.hasText(nickname))
            user.changeNickname(nickname);
    }

    /**
     * 이미지 체크후 변경
     */
    private void checkImageFileAndChange(MultipartFile newProfileImageFile, User user) {
        // 기존 프로필 이미지가 없는 경우
        if (!StringUtils.hasText(user.getProfileImageUrl())) {
            String newProfileImageUrl = imageFileStore.uploadFile(newProfileImageFile);
            user.changeProfileImageUrl(newProfileImageUrl);
            return;
        }

        // 프로필 이미지를 변경하는 경우
        if (newProfileImageFile != null && !newProfileImageFile.isEmpty()) {
            imageFileStore.deleteFile(user.getProfileImageUrl());
            String newProfileImageUrl = imageFileStore.uploadFile(newProfileImageFile);
            user.changeProfileImageUrl(newProfileImageUrl);
        }
    }

    /**
     * 닉네임 중복 검증
     */
    private void validateDuplicateNickname(String nickname) {
        if (userRepository.existsByNickname(nickname))
            throw new RuntimeException("중복된 닉네임이 존재합니다.");
    }
}
