package com.instagram.numble_instagram.model.entity.user;

import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE FOLLOW SET DELETED = TRUE WHERE MSG_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "FOLLOW", indexes = {
	@Index(name = "FOLLOW_INDEX1", columnList = "DELETED"),
})
@Comment("팔로우 정보 테이블")
public class FollowEntity {

	@Builder
	public FollowEntity(Long followId, UserEntity user, UserEntity followUser) {
		this.followId = followId;
		this.user = user;
		this.followUser = followUser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOLLOW_ID")
	@Comment("팔로우 ID")
	private Long followId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@Comment("본인 유저 ID")
	private UserEntity user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FOLLOW_USER_ID", referencedColumnName = "USER_ID")
	@Comment("팔로우한 유저 ID")
	private UserEntity followUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@UpdateTimestamp
	@Column(name = "UPD_DATE", nullable = false)
	@Comment("수정 날짜")
	private LocalDateTime updDate;

	@Column(name = "DELETED", nullable = false)
	@Comment("삭제 여부")
	private boolean deleted = Boolean.FALSE;
}
