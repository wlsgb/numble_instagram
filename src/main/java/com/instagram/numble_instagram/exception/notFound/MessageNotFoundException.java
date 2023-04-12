package com.instagram.numble_instagram.exception.notFound;

import com.instagram.numble_instagram.exception.notFound.base.NotFoundException;

public class MessageNotFoundException extends NotFoundException {
    public MessageNotFoundException() {
        addValidation("message", "메세지 정보를 찾을 수 없습니다.");
    }
}
