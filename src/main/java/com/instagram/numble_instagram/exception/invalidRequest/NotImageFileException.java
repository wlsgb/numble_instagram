package com.instagram.numble_instagram.exception.invalidRequest;


import com.instagram.numble_instagram.exception.invalidRequest.base.InvalidRequestException;

public class NotImageFileException extends InvalidRequestException {
    public NotImageFileException() {
        addValidation("multipartFile", "이미지 파일 형식이 아닙니다.");
    }
}
