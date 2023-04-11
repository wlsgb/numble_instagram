package com.instagram.numble_instagram.model.entity.jwt;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "REFRESH_TOKEN", indexes = {
    @Index(name = "REFRESH_TOKEN_INDEX1", columnList = "KEY_USER_ID"),
    @Index(name = "REFRESH_TOKEN_INDEX2", columnList = "REFRESH_TOKEN")
})
@Comment("리프레시 토큰 테이블")
public class RefreshToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "REFRESH_TOKEN_ID", nullable = false)
  private Long refreshTokenId;

  @Column(name = "REFRESH_TOKEN", nullable = false)
  private String refreshToken;

  @Column(name = "KEY_USER_ID", nullable = false)
  private String keyUserId;

}
