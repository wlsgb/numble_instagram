package com.instagram.numble_instagram.model.entity.follow;

import com.instagram.numble_instagram.model.entity.user.User;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "FOLLOW")
@Comment("팔로우 정보 테이블")
public class Follow {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOLLOW_ID")
	@Comment("팔로우 ID")
	private Long followId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FROM_USER_ID")
	@Comment("본인 유저 ID")
	private User fromUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TO_USER_ID")
	@Comment("팔로우한 유저 ID")
	private User toUser;

	@CreationTimestamp
	@Column(name = "REG_DATE", nullable = false)
	@Comment("등록 날짜")
	private LocalDateTime regDate;


	@Builder
	public Follow(User toUser, User fromUser) {
		this.toUser = toUser;
		this.fromUser = fromUser;
	}

	/**
	 * 팔로우 요청
	 */
	public static Follow followUser(User fromUser, User toUser) {
		return Follow.builder()
				.fromUser(fromUser)
				.toUser(toUser)
				.build();
	}

	/**
	 * 유저 검증
	 */
	public boolean isFromUser(User fromUser) {
		return this.fromUser.equals(fromUser);
	}

}
