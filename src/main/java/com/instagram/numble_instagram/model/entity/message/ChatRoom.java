package com.instagram.numble_instagram.model.entity.message;

import com.instagram.numble_instagram.model.entity.user.User;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "CHAT_ROOM")
@Comment("채팅방 테이블")
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHAT_ROOM_ID")
	@Comment("채팅방 ID")
	private Long chatRoomId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CREATE_CHAT_ROOM_USER_ID")
	@Comment("챗 유저 ID")
	private User createChatRoomUser;

	@Column(name = "LAST_MESSAGE")
	@Comment("마지막으로 보낸 메세지")
	private String lastMessage;

	@CreationTimestamp
	@Column(name = "REG_DATE", updatable = false, nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@UpdateTimestamp
	@Column(name = "UPD_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime updDate;

	@OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Message> messageList = new ArrayList<>();

	@Builder
	public ChatRoom(User createChatRoomUser) {
		this.createChatRoomUser = createChatRoomUser;
	}

	/**
	 * 채팅방 등록
	 */
	public static ChatRoom register(User createChatRoomUser) {
		return ChatRoom.builder()
				.createChatRoomUser(createChatRoomUser)
				.build();
	}

	/**
	 * 마지막 전송 메세지 변경
	 */
	public void changeLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}
}
