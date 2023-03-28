package com.instagram.numble_instagram.model.entity.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "USER", indexes = {
	@Index(name = "USER_INDEX1", columnList = "DEL_YN", unique = true),
})
@Comment("유저 테이블")
public class UserEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID")
	@Comment("유저 ID")
	private Long userId; // 유저 ID

	@Column(name = "NICKNAME", length = 100, nullable = false, unique = true)
	@Comment("닉네임")
	private String nickname;

	@OneToOne(fetch = FetchType.LAZY)
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

	@Column(name = "DEL_YN", length = 1, nullable = false)
	@ColumnDefault("'N'")
	@Comment("삭제 여부")
	private Character delYn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEL_DATE")
	@Comment("삭제 날짜")
	private LocalDateTime delDate;

}
