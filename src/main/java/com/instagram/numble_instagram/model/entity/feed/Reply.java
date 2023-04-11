package com.instagram.numble_instagram.model.entity.feed;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.instagram.numble_instagram.model.entity.user.User;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE REPLY SET DELETED = TRUE WHERE USER_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "REPLY", indexes = {
	@Index(name = "REPLY_INDEX1", columnList = "DELETED"),
})
public class Reply {

	@Builder
	public Reply(Long replyId, String content, Comment comment, User regUser) {
		this.replyId = replyId;
		this.content = content;
		this.comment = comment;
		this.regUser = regUser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPLY_ID")
	private Long replyId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(2000)")
	@org.hibernate.annotations.Comment("답글 내용")
	private String content;

	@ManyToOne
	@JoinColumn(name = "COMMENT_ID")
	@org.hibernate.annotations.Comment("답글 ID")
	private Comment comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@org.hibernate.annotations.Comment("답글 작성한 유저 ID")
	private User regUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@org.hibernate.annotations.Comment("등록 날짜")
	private LocalDateTime regDate;

	@UpdateTimestamp
	@Column(name = "UPD_DATE", nullable = false)
	@org.hibernate.annotations.Comment("수정 날짜")
	private LocalDateTime updDate;

	@Column(name = "DELETED", nullable = false)
	@org.hibernate.annotations.Comment("삭제 여부")
	private boolean deleted = Boolean.FALSE;
}
