package com.instagram.numble_instagram.exception.invalidRequest;

import com.instagram.numble_instagram.exception.invalidRequest.base.InvalidRequestException;

public class NotFromUserException extends InvalidRequestException {
    public NotFromUserException() {
        addValidation("fromUser", "팔로우 요청자가 아닙니다.");
    }
}
