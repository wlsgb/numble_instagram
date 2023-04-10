package com.instagram.numble_instagram.exception.invalidRequest.base;

import com.instagram.numble_instagram.exception.CustomException;

public class InvalidRequestException extends CustomException {

    private static final String MESSAGE = "잘못된 요청입니다.";
    private static final int STATUS_CODE = 400;

    public InvalidRequestException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return STATUS_CODE;
    }
}
