package com.instagram.numble_instagram.model.entity.post;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.instagram.numble_instagram.model.entity.user.UserEntity;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name = "COMMENT", indexes = {
	@Index(name = "COMMENT_INDEX1", columnList = "DEL_YN", unique = true),
})
@Comment("댓글 테이블")
public class CommentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Comment("댓글 ID")
	private Long commentId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(2000)")
	@Comment("내용")
	private String content;

	@ManyToOne
	@JoinColumn(name = "POST_ID")
	@Comment("글 ID")
	private PostEntity post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@Comment("유저 ID")
	private UserEntity user;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@UpdateTimestamp
	@Column(name = "UPD_DATE", nullable = false)
	@Comment("수정 날짜")
	private LocalDateTime UpdDate;

	@Column(name = "DEL_YN", length = 1, nullable = false)
	@ColumnDefault("'N'")
	@Comment("삭제 여부")
	private Character delYn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEL_DATE")
	@Comment("삭제 날짜")
	private LocalDateTime delDate;

	@OneToMany(mappedBy = "comment")
	private List<ReplyEntity> replyList;
}
