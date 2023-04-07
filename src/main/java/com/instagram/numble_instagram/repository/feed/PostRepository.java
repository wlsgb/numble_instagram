package com.instagram.numble_instagram.repository.feed;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.feed.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

	List<PostEntity> findAllByOrderByPostIdDesc(Pageable pageable);
	List<PostEntity> findByPostIdLessThanOrderByPostIdDesc(Long postId, Pageable pageable);
	Boolean existsByPostIdLessThan(Long postId);
}
