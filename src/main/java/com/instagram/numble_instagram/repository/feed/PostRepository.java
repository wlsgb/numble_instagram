package com.instagram.numble_instagram.repository.feed;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.feed.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllByOrderByPostIdDesc(Pageable pageable);
	List<Post> findByPostIdLessThanOrderByPostIdDesc(Long postId, Pageable pageable);
	Boolean existsByPostIdLessThan(Long postId);
}
