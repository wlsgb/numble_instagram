package com.instagram.numble_instagram.model.entity.feed;

import com.instagram.numble_instagram.model.entity.user.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "POST")
@Comment("글 테이블")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    @Comment("글 ID")
    private Long postId;

    @Column(name = "CONTENT", columnDefinition = "NVARCHAR(2000)")
    @Comment("글 내용")
    private String content;

    @Column(name = "IMAGE_URL")
    @Comment("이미지 URL")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    @Comment("글 등록한 유저 ID")
    private UserEntity regUser;

    @CreationTimestamp
    @Column(name = "REG_DATE", updatable = false, nullable = false)
    @Comment("등록 날짜")
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "UPD_DATE", nullable = false)
    @Comment("수정 날짜")
    private LocalDateTime updDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> commentList = new ArrayList<>();

    @Builder
    public PostEntity(String content, String imageUrl, UserEntity regUser) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.regUser = regUser;
    }

    /**
     * 글 등록
     */
    public static PostEntity register(String content, String imageUrl, UserEntity regUser) {
        return PostEntity.builder()
                .content(content)
                .imageUrl(imageUrl)
                .regUser(regUser)
                .build();
    }

    /**
     * 글 내용 변경
     */
    public void changeContent(String newContent) {
        if (this.content.equals(newContent))
            return;
        this.content = newContent;
    }

    /**
     * 이미지 URL 변경
     */
    public void changeImageUrl(String newImageUrl) {
        if (this.imageUrl.equals(newImageUrl))
            return;
        this.imageUrl = newImageUrl;
    }

    /**
     * 댓글 추가
     */
    public void addComment(CommentEntity newComment) {
        this.commentList.add(newComment);
    }

    /**
     * 이미지 URL 존재 여부 확인
     */
    public boolean isImageUrl() {
        return StringUtils.hasText(imageUrl);
    }

    /**
     * 작성자 여부 확인
     */
    public boolean isRegUser(UserEntity user) {
        return this.regUser.getUserId() == user.getUserId();
    }

}
