package com.instagram.numble_instagram.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.post.CommentEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
