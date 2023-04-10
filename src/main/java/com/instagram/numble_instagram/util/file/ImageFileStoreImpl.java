package com.instagram.numble_instagram.util.file;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageFileStoreImpl implements FileStore {

    private static final String PREFIX = "https://aws.s3";
    private static final String DEFAULT_IMAGE_URI = "/default-image.jpg";

    /**
     * 저장할 파일 이름 생성
     */
    private String createSaveFileName(String originalFilename) {
        String extension = FileUtil.extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    @Override
    public String uploadFile(MultipartFile file) {
        if (file == null || file.isEmpty())
            return null;

        // 이미지 파일 검증
        validateContentTypeImage(file);

        String originalFileName = file.getOriginalFilename();
        String saveFilename = createSaveFileName(originalFileName);

        // TODO. S3 파일 업로드 로직 추가

        return PREFIX + "/" + saveFilename;
    }

    @Override
    public void deleteFile(String fileUrl) {
        if (fileUrl.equals(DEFAULT_IMAGE_URI))
            return;

        // TODO. S3 파일 삭제 로직 추가
    }

    /**
     * 이미지 파일 형식 검증
     */
    private static void validateContentTypeImage(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType != null && !contentType.startsWith("image")) {
            throw new RuntimeException("이미지 타입이 아닙니다.");
        }
    }

}
