package com.instagram.numble_instagram.model.entity.feed;

import com.instagram.numble_instagram.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;
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
@org.hibernate.annotations.Comment("글 테이블")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    @org.hibernate.annotations.Comment("글 ID")
    private Long postId;

    @Column(name = "CONTENT", columnDefinition = "NVARCHAR(2000)")
    @org.hibernate.annotations.Comment("글 내용")
    private String content;

    @Column(name = "IMAGE_URL")
    @org.hibernate.annotations.Comment("이미지 URL")
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @org.hibernate.annotations.Comment("글 등록한 유저 ID")
    private User regUser;

    @CreationTimestamp
    @Column(name = "REG_DATE", updatable = false, nullable = false)
    @org.hibernate.annotations.Comment("등록 날짜")
    private LocalDateTime regDate;

    @UpdateTimestamp
    @Column(name = "UPD_DATE", nullable = false)
    @org.hibernate.annotations.Comment("수정 날짜")
    private LocalDateTime updDate;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(String content, String imageUrl, User regUser) {
        this.content = content;
        this.imageUrl = imageUrl;
        this.regUser = regUser;
    }

    /**
     * 글 등록
     */
    public static Post register(String content, String imageUrl, User regUser) {
        return Post.builder()
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
    public void addComment(Comment newComment) {
        this.commentList.add(newComment);
    }

    /**
     * 이미지 URL 존재 여부 확인
     */
    public boolean isImageUrl() {
        return StringUtils.hasText(imageUrl);
    }

    /**
     * 등록자 여부 확인
     */
    public boolean isRegUser(User user) {
        return this.regUser.equals(user);
    }

}
