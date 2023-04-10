package com.instagram.numble_instagram.exception.notFound;

import com.instagram.numble_instagram.exception.notFound.base.NotFoundException;

public class ReplyNotFoundException extends NotFoundException {
    public ReplyNotFoundException() {
        addValidation("reply", "답글 정보를 찾을 수 없습니다.");
    }
}
