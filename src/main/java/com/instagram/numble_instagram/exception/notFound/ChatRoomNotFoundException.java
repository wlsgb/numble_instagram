package com.instagram.numble_instagram.exception.notFound;

import com.instagram.numble_instagram.exception.notFound.base.NotFoundException;

public class ChatRoomNotFoundException extends NotFoundException {
    public ChatRoomNotFoundException() {
        addValidation("chatRoom", "챗팅방 정보를 찾을 수 없습니다.");
    }
}
