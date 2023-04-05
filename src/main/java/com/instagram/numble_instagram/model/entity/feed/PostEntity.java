package com.instagram.numble_instagram.model.entity.feed;

import java.io.Serializable;
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

import com.instagram.numble_instagram.model.entity.image.ImageEntity;
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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE POST SET DELETED = TRUE WHERE USER_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "POST", indexes = {
	@Index(name = "POST_INDEX1", columnList = "DELETED"),
})
@Comment("글 테이블")
public class PostEntity implements Serializable {

	@Builder
	public PostEntity(String content, ImageEntity image, UserEntity user) {
		this.content = content;
		this.image = image;
		this.user = user;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POST_ID")
	@Comment("글 ID")
	private Long postId;

	@Column(name = "CONTENT", columnDefinition = "NVARCHAR(2000)")
	@Comment("내용")
	private String content;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IMAGE_ID")
	@Comment("이미지 ID")
	private ImageEntity image;

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

	@Column(name = "DELETED", nullable = false)
	@Comment("삭제 여부")
	private boolean deleted = Boolean.FALSE;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentEntity> commentList;

}
