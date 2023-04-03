package com.instagram.numble_instagram.model.entity.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import com.instagram.numble_instagram.model.entity.image.ImageEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
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
@SQLDelete(sql = "UPDATE USER SET DELETED = TRUE WHERE USER_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "USER", indexes = {
	@Index(name = "USER_INDEX1", columnList = "DELETED"),
})
@Comment("유저 테이블")
public class UserEntity implements Serializable {

	@Builder
	public UserEntity(String nickname, ImageEntity image) {
		this.nickname = nickname;
		this.image = image;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	@Comment("유저 ID")
	private Long userId; // 유저 ID

	@Column(name = "NICKNAME", length = 100, nullable = false, unique = true)
	@Comment("닉네임")
	private String nickname;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IMAGE_ID")
	@Comment("이미지")
	private ImageEntity image;

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
}
