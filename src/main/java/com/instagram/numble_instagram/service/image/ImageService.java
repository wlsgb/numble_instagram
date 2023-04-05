package com.instagram.numble_instagram.service.image;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.instagram.numble_instagram.model.entity.image.ImageEntity;
import com.instagram.numble_instagram.model.entity.user.UserEntity;
import com.instagram.numble_instagram.repository.image.ImageRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;

	/**
	 * 이미지 URL 생성
	 */
	private String createImageUrl(MultipartFile imageFile) {
		return ("/" + UUID.randomUUID() + "/" + imageFile.getName()).replaceAll(" ", "+");
	}

	/**
	 * 유일한 이미지 URL 생성
	 */
	private String primaryImageUrl(MultipartFile imageFile) {
		String newImageUrl = createImageUrl(imageFile);
		while (imageRepository.existsByImageUrl(newImageUrl)) {
			newImageUrl = createImageUrl(imageFile);
		}
		return newImageUrl;
	}

	/**
	 * 이미지 저장
	 */
	public ImageEntity saveImage(MultipartFile imageFile, UserEntity regUser) {
		// 신규 이미지 URL
		String newImageUrl = primaryImageUrl(imageFile);
		// 이미지 경로 저장
		return imageRepository.save(
			ImageEntity.builder()
				.imageUrl(newImageUrl)
				.regUser(regUser)
				.build()
		);
	}

	/**
	 * 이미지 삭제
	 */
	public void deleteImage(Long imageId) {
		imageRepository.deleteById(imageId);
	}

}
