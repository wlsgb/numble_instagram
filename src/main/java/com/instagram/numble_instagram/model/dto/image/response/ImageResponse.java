package com.instagram.numble_instagram.model.dto.image.response;

import com.instagram.numble_instagram.model.entity.image.ImageEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ImageResponse {
	private Long imageId;
	private String imageUrl;

	public static ImageResponse convertResponse(ImageEntity image) {
		if (image == null)
			return new ImageResponse();

		return ImageResponse.builder()
			.imageId(image.getImageId())
			.imageUrl(image.getImageUrl())
			.build();
	}
}
