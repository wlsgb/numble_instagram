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
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "COMMENT")
@org.hibernate.annotations.Comment("댓글 테이블")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@org.hibernate.annotations.Comment("댓글 ID")
	private Long commentId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(1000)")
	@org.hibernate.annotations.Comment("댓글 내용")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_ID")
	@org.hibernate.annotations.Comment("글 ID")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@org.hibernate.annotations.Comment("댓글 작성한 유저 ID")
	private User regUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@org.hibernate.annotations.Comment("등록 날짜")
	private LocalDateTime regDate;

	@UpdateTimestamp
	@Column(name = "UPD_DATE", nullable = false)
	@org.hibernate.annotations.Comment("수정 날짜")
	private LocalDateTime updDate;

	@OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reply> replyList = new ArrayList<>();

	@Builder
	public Comment(String content, Post post, User regUser) {
		this.content = content;
		this.post = post;
		this.regUser = regUser;
	}

	/**
	 * 댓글 등록
	 */
	public static Comment register(User regUser, Post post, String content) {
		return Comment.builder()
				.regUser(regUser)
				.post(post)
				.content(content)
				.build();
	}

	/**
	 * 댓글 내용 변경
	 */
	public void changeContent(String newContent) {
		if (this.content.equals(newContent))
			return;
		this.content = newContent;
	}

	/**
	 * 답글 추가
	 */
	public void addReply(Reply newReply) {
		this.replyList.add(newReply);
	}

	/**
	 * 등록자 여부 확인
	 */
	public boolean isRegUser(User user) {
		return this.regUser.equals(user);
	}

}
