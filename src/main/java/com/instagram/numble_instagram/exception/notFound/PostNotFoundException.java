package com.instagram.numble_instagram.exception.notFound;

import com.instagram.numble_instagram.exception.notFound.base.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException() {
        addValidation("post", "글 정보를 찾을 수 없습니다.");
    }
}
