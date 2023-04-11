package com.instagram.numble_instagram.repository.feed;

import com.instagram.numble_instagram.model.entity.feed.Post;
import com.instagram.numble_instagram.model.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByRegUserOrderByPostIdDesc(User regUser, Pageable pageable);

    List<Post> findByRegUserAndPostIdLessThanOrderByPostIdDesc(User regUser, Long postId, Pageable pageable);

    Boolean existsByRegUserAndPostIdLessThan(User regUser, Long postId);
}
