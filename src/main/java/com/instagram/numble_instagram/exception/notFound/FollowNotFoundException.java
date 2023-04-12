package com.instagram.numble_instagram.exception.notFound;

import com.instagram.numble_instagram.exception.notFound.base.NotFoundException;

public class FollowNotFoundException extends NotFoundException {
    public FollowNotFoundException() {
        addValidation("follow", "팔로우 정보를 찾을 수 없습니다.");
    }
}
