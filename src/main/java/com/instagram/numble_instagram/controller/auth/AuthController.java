package com.instagram.numble_instagram.controller.auth;

import com.instagram.numble_instagram.config.jwt.JwtTokenProvider;
import com.instagram.numble_instagram.config.security.SecurityUser;
import com.instagram.numble_instagram.model.dto.jwt.RefreshApiResponseMessage;
import com.instagram.numble_instagram.model.dto.jwt.Token;
import com.instagram.numble_instagram.model.dto.user.request.LoginRequest;
import com.instagram.numble_instagram.model.dto.user.request.UserJoinRequest;
import com.instagram.numble_instagram.model.dto.user.request.UserModifyRequest;
import com.instagram.numble_instagram.model.dto.user.response.UserResponse;
import com.instagram.numble_instagram.service.jwt.JwtService;
import com.instagram.numble_instagram.service.user.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtService jwtService;
    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping(value = "/join", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> joinUser(UserJoinRequest joinRequest) {
        // 회원 가입 처리
        UserResponse newUser = authService.join(joinRequest);
        // 성공
        return ResponseEntity.ok(newUser);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Token> getLoginToken(
            @RequestBody LoginRequest loginRequest
    ) {
        log.info("로그인 요청 - [닉네임: {}]", loginRequest.getNickname());
        UserResponse loginUser = authService.login(loginRequest);
        // 토큰 생성
        Token token = jwtTokenProvider.createToken(
                String.valueOf(loginUser.getUserId()),
                loginUser.getNickname()
        );
        jwtService.signIn(token);
        return ResponseEntity.ok(token);
    }

    /**
     * 프로필 수정
     */
    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserResponse> modifyUser(
            UserModifyRequest dto,
            @AuthenticationPrincipal SecurityUser user
    ) {
        dto.setUserId(user.getUser().getUserId());
        UserResponse modifiedUser = authService.modify(dto);
        return ResponseEntity.ok(modifiedUser);
    }

    /**
     * 회원 탈퇴
     */
    @DeleteMapping(value = "/user")
    public ResponseEntity<HttpStatus> deleteUser(
            @AuthenticationPrincipal SecurityUser user
    ) {
        authService.delete(user.getUser().getUserId());
        return ResponseEntity.ok().build();
    }

    /**
     * jwt refresh token 검증 후 access토큰 발행
     */
    @PostMapping(value = "/jwt-refresh")
    public ResponseEntity<RefreshApiResponseMessage> validateRefreshToken(
            @RequestBody Token token
    ) {
        Map<String, String> map = jwtService.validateRefreshToken(token.getRefreshToken());
        RefreshApiResponseMessage refreshApiResponseMessage = new RefreshApiResponseMessage(map);
        if (map.get("status").equals("402"))
            return new ResponseEntity<>(refreshApiResponseMessage, HttpStatus.UNAUTHORIZED);
        return ResponseEntity.ok(refreshApiResponseMessage);
    }
}