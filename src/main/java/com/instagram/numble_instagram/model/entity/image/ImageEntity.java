package com.instagram.numble_instagram.model.entity.image;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
@Table(name = "IMAGE", indexes = {
	@Index(name = "IMAGE_INDEX_01", columnList = "DEL_YN", unique = true)
})
@Comment("이미지 테이블")
public class ImageEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IMAGE_ID")
	private Long imageId;

	@Column(name = "IMAGE_URL", length = 200, nullable = false, unique = true)
	private String imageUrl;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;

	@Column(name = "DEL_YN", length = 1, nullable = false)
	@ColumnDefault("'N'")
	@Comment("삭제 여부")
	private Character delYn;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEL_DATE")
	@Comment("삭제 날짜")
	private LocalDateTime delDate;
}
