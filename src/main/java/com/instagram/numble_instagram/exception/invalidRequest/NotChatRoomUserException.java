package com.instagram.numble_instagram.exception.invalidRequest;

import com.instagram.numble_instagram.exception.invalidRequest.base.InvalidRequestException;

public class NotChatRoomUserException extends InvalidRequestException {
    public NotChatRoomUserException() {
        addValidation("chatRoomUser", "채팅방 유저가 아닙니다.");
    }
}
