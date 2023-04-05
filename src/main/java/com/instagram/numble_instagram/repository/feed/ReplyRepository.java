package com.instagram.numble_instagram.repository.feed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.feed.ReplyEntity;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {
}
