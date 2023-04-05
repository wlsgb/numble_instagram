package com.instagram.numble_instagram.model.entity.feed;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
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
@SQLDelete(sql = "UPDATE COMMENT SET DELETED = TRUE WHERE USER_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "COMMENT", indexes = {
	@Index(name = "COMMENT_INDEX1", columnList = "DELETED"),
})
@Comment("댓글 테이블")
public class CommentEntity {

	@Builder
	public CommentEntity(Long commentId, String content, PostEntity post, UserEntity regUser) {
		this.commentId = commentId;
		this.content = content;
		this.post = post;
		this.regUser = regUser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("댓글 ID")
	private Long commentId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(2000)")
	@Comment("댓글 내용")
	private String content;

	@ManyToOne
	@JoinColumn(name = "POST_ID")
	@Comment("글 ID")
	private PostEntity post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@Comment("댓글 작성한 유저 ID")
	private UserEntity regUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@UpdateTimestamp
	@Column(name = "UPD_DATE", nullable = false)
	@Comment("수정 날짜")
	private LocalDateTime UpdDate;

	@Column(name = "DELETED", nullable = false)
	@Comment("삭제 여부")
	private boolean deleted = Boolean.FALSE;

	@OneToMany(mappedBy = "comment")
	private List<ReplyEntity> replyList;
}
