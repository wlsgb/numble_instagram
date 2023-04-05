package com.instagram.numble_instagram.model.entity.message;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.instagram.numble_instagram.model.entity.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE MESSAGE SET DELETED = TRUE WHERE MSG_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "MESSAGE", indexes = {
	@Index(name = "MESSAGE_INDEX1", columnList = "DELETED"),
})
@Comment("메세지 테이블")
public class MessageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MSG_ID")
	@Comment("메세지 ID")
	private Long msgId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(1000)")
	@Comment("내용")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SEND_USER_ID")
	@Comment("보낸 유저 ID")
	private UserEntity sendUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECEIVE_USER_ID")
	@Comment("받은 유저 ID")
	private UserEntity receiveUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@Column(name = "DELETED", nullable = false)
	@Comment("삭제 여부")
	private boolean deleted = Boolean.FALSE;
}
