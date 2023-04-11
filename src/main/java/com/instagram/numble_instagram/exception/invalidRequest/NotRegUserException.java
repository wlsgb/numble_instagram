package com.instagram.numble_instagram.exception.invalidRequest;

import com.instagram.numble_instagram.exception.invalidRequest.base.InvalidRequestException;

public class NotRegUserException extends InvalidRequestException {
    public NotRegUserException() {
        addValidation("regUser", "글 등록자가 아닙니다.");
    }
}
