package com.instagram.numble_instagram.exception.notFound;

import com.instagram.numble_instagram.exception.notFound.base.NotFoundException;

public class UserChatRoomMappedNotFoundException extends NotFoundException {
    public UserChatRoomMappedNotFoundException() {
        addValidation("userChatRoomMapped", "회원 채팅방 매핑 정보를 찾을 수 없습니다.");
    }
}
