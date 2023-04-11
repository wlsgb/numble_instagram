package com.instagram.numble_instagram.model.entity.feed;

import com.instagram.numble_instagram.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "REPLY")
public class Reply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPLY_ID")
	private Long replyId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(1000)")
	@org.hibernate.annotations.Comment("답글 내용")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
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

	@Builder
	public Reply(String content, Comment comment, User regUser) {
		this.content = content;
		this.comment = comment;
		this.regUser = regUser;
	}

	/**
	 * 답글 등록
	 */
	public static Reply register(User regUser, Comment comment, String content) {
		return Reply.builder()
				.regUser(regUser)
				.comment(comment)
				.content(content)
				.build();
	}

	/**
	 *  답글 내용 변경
	 */
	public void changeContent(String newContent) {
		if (this.content.equals(newContent))
			return;
		this.content = newContent;
	}

	/**
	 * 등록자 여부 확인
	 */
	public boolean isRegUser(User user) {
		return this.regUser.equals(user);
	}

}
