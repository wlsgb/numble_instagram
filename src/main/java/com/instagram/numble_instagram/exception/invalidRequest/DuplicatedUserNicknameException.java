package com.instagram.numble_instagram.exception.invalidRequest;

import com.instagram.numble_instagram.exception.invalidRequest.base.InvalidRequestException;

public class DuplicatedUserNicknameException extends InvalidRequestException {
    public DuplicatedUserNicknameException() {
        addValidation("nickname", "닉네임이 이미 존재합니다.");
    }
}
