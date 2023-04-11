package com.instagram.numble_instagram.model.entity.user;

import com.instagram.numble_instagram.fixture.user.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    @DisplayName("새로운 nickname은 수정되어야 함")
    void changeNicknameTest() {
        User user = UserFixture.create(1L, "옛날 닉네임");
        user.changeNickname("새로운 닉네임");
        assertEquals("새로운 닉네임", user.getNickname());
    }

    @Test
    @DisplayName("같은 nickname은 수정되지 않아야 함")
    void changeSameNicknameTest() {
        User user = UserFixture.create(1L, "옛날 닉네임");
        user.changeNickname("옛날 닉네임");
        assertEquals("옛날 닉네임", user.getNickname());
    }

}