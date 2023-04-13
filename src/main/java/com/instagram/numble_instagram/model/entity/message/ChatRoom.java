package com.instagram.numble_instagram.model.entity.message;

import com.instagram.numble_instagram.model.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	@JoinColumn(name = "LAST_SEND_USER_ID")
	@Comment("마지막으로 전송한 유저 ID")
	private User lastSendUser;

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

	@OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserChatRoomMapped> userChatRoomMappedList = new ArrayList<>();

	@Builder
	public ChatRoom(User lastSendUser) {
		this.lastSendUser = lastSendUser;
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
	public static ChatRoom register(User lastSendUser) {
		return ChatRoom.builder()
				.lastSendUser(lastSendUser)
				.build();
	}

	/**
	 * 메세지 리스트 추가
	 */
	public void addMessage(Message newMessage) {
		this.messageList.add(newMessage);
	}

	/**
	 * 메세지 리스트 추가
	 */
	public void addUserChatRoomMapped(UserChatRoomMapped newUserChatRoomMapped) {
		if (userChatRoomMappedList.stream()
				.noneMatch(userChatRoomMapped -> userChatRoomMapped.equals(newUserChatRoomMapped)))
			this.userChatRoomMappedList.add(newUserChatRoomMapped);
	}

	/**
	 * 마지막으로 전송한 유저
	 */
	public void changeLastSendUser(User user) {
		if (!this.lastSendUser.equals(user))
			this.lastSendUser = user;
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
