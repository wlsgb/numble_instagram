package com.instagram.numble_instagram.model.entity.feed;

import com.instagram.numble_instagram.model.entity.user.User;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE COMMENT SET DELETED = TRUE WHERE USER_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "COMMENT", indexes = {
	@Index(name = "COMMENT_INDEX1", columnList = "DELETED"),
})
@org.hibernate.annotations.Comment("댓글 테이블")
public class Comment {

	@Builder
	public Comment(Long commentId, String content, Post post, User regUser) {
		this.commentId = commentId;
		this.content = content;
		this.post = post;
		this.regUser = regUser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@org.hibernate.annotations.Comment("댓글 ID")
	private Long commentId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(2000)")
	@org.hibernate.annotations.Comment("댓글 내용")
	private String content;

	@ManyToOne
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

	@Column(name = "DELETED", nullable = false)
	@org.hibernate.annotations.Comment("삭제 여부")
	private boolean deleted = Boolean.FALSE;

	@OneToMany(mappedBy = "comment")
	private List<Reply> replyList;
}
