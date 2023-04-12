package com.instagram.numble_instagram.controller.user;

import com.instagram.numble_instagram.model.dto.user.response.ProfileResponse;
import com.instagram.numble_instagram.usecase.user.ReadProfileUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final ReadProfileUseCase readProfileUseCase;

    /**
     * 프로필 조회
     */
    @GetMapping(value = "/profile/{userId}")
    public ResponseEntity<ProfileResponse> getUserProfile(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(readProfileUseCase.execute(userId));
    }
}
