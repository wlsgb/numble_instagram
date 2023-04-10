package com.instagram.numble_instagram.model.entity.user;

import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "USER", indexes = {
        @Index(name = "USER_INDEX1", columnList = "NICKNAME"),
})
@Comment("유저 테이블")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    @Comment("유저 ID")
    private Long userId;

    @Column(name = "NICKNAME", length = 20, nullable = false, unique = true)
    @Comment("닉네임")
    private String nickname;

    @Column(name = "PROFILE_IMAGE_URL")
    @Comment("프로필 이미지 URL")
    private String profileImageUrl;

    @CreationTimestamp
    @Column(name = "REG_DATE", updatable = false, nullable = false)
    @Comment("등록 날짜")
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "UPD_DATE", nullable = false)
    @Comment("수정 날짜")
    private LocalDateTime updDate;

    @Builder
    public UserEntity(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserEntity join(String nickname, String profileImageUrl) {
        return UserEntity.builder()
                .nickname(nickname)
                .profileImageUrl(profileImageUrl)
                .build();
    }

    public void changeNickname(String newNickname) {
        if (this.nickname.equals(newNickname))
            return;
        this.nickname = newNickname;
    }

    public void changeProfileImageUrl(String newProfileImageUrl) {
        this.profileImageUrl = newProfileImageUrl;
    }
}
