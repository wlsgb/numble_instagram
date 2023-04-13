package com.instagram.numble_instagram.model.entity.message;

import com.instagram.numble_instagram.model.entity.user.User;
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
@Table(name = "MESSAGE")
@Comment("메세지 테이블")
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MSG_ID")
	@Comment("메세지 ID")
	private Long msgId;

	@Column(name = "CONTENT", length = 500)
	@Comment("메세지 내용")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CHAT_ROOM_ID")
	@Comment("메세지 보낸 채팅방 ID")
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SEND_USER_ID")
	@Comment("메세지 보낸 유저 ID")
	private User sendUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@Builder
	public Message(String content, ChatRoom chatRoom, User sendUser) {
		this.content = content;
		this.chatRoom = chatRoom;
		this.sendUser = sendUser;
	}

	/**
	 * 메세지 전송
	 */
	public static Message sendMessage(String content, ChatRoom chatRoom, User sendUser) {
		return Message.builder()
				.content(content)
				.chatRoom(chatRoom)
				.sendUser(sendUser)
				.build();
	}
}
