package com.instagram.numble_instagram.model.entity.image;

import java.io.Serializable;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@SQLDelete(sql = "UPDATE IMAGE SET DELETED = TRUE WHERE IMAGE_ID = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "DELETED = FALSE")
@Entity
@Table(name = "IMAGE", indexes = {
	@Index(name = "IMAGE_INDEX_01", columnList = "DELETED"),
	@Index(name = "IMAGE_INDEX_02", columnList = "IMAGE_URL")
})
@Comment("이미지 테이블")
public class ImageEntity implements Serializable {

	@Builder
	public ImageEntity(String imageUrl, UserEntity regUser) {
		this.imageUrl = imageUrl;
		this.regUser = regUser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMAGE_ID")
	@Comment("이미지 ID")
	private Long imageId;

	@Column(name = "IMAGE_URL", length = 200, nullable = false, unique = true)
	@Comment("이미지 출력 URL")
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	@Comment("이미지 저장한 유저 ID")
	private UserEntity regUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@Column(name = "DELETED", nullable = false)
	@Comment("삭제 여부")
	private boolean deleted = Boolean.FALSE;
}
