package com.instagram.numble_instagram.repository.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.post.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
