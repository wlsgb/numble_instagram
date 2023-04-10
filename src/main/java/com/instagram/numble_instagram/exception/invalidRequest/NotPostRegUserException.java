package com.instagram.numble_instagram.exception.invalidRequest;

import com.instagram.numble_instagram.exception.invalidRequest.base.InvalidRequestException;

public class NotPostRegUserException extends InvalidRequestException {
    public NotPostRegUserException() {
        addValidation("regUser", "글 등록자가 아닙니다.");
    }
}
