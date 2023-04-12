package com.instagram.numble_instagram.model.entity.message;

import com.instagram.numble_instagram.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "USER_CHAT_ROOM_MAPPED",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "USER_CHAT_ROOM_MAPPED_UNIQUE",
                        columnNames = {"CHAT_ROOM_ID", "CHAT_USER_ID"}
                )
        })
@Comment("유저의 채팅방 테이블")
public class UserChatRoomMapped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_CHAT_ROOM_MAPPED_ID")
    @Comment("채팅방 ID")
    private Long chatRoomUserId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_ROOM_ID")
    @Comment("채팅방 ID")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHAT_USER_ID")
    @Comment("채팅 유저 ID")
    private User chatUser;

    @Builder
    public UserChatRoomMapped(ChatRoom chatRoom, User chatUser) {
        this.chatRoom = chatRoom;
        this.chatUser = chatUser;
    }

    /**
     * 채팅방 유저 추가
     */
    public static UserChatRoomMapped plusUser(ChatRoom chatRoom, User chatUser) {
        return UserChatRoomMapped.builder()
                .chatRoom(chatRoom)
                .chatUser(chatUser)
                .build();
    }
}
