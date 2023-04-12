package com.instagram.numble_instagram.model.entity.message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import com.instagram.numble_instagram.model.entity.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@Column(name = "GROUP_CHAT")
	@Comment("그룹 채팅방 여부")
	private boolean groupChat = Boolean.FALSE;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ChatRoom chatRoom = (ChatRoom) o;
		return Objects.equals(chatRoomId, chatRoom.chatRoomId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(chatRoomId);
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

	/**
	 * 그룹 채팅방 여부 변경
	 */
	public void changeGroupChat(boolean isGroupChat) {
		this.groupChat = isGroupChat;
	}
}
