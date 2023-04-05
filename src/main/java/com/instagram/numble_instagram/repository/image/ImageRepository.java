package com.instagram.numble_instagram.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.instagram.numble_instagram.model.entity.image.ImageEntity;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

	boolean existsByImageUrl(String imageUrl);
}
