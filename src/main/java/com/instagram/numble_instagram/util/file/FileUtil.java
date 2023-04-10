package com.instagram.numble_instagram.util.file;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
    /**
     * 파일 객체 존재 여부
     */
    public static boolean existFile(MultipartFile file) {
        return file != null && file.isEmpty();
    }

    /**
     * 확장자 추출
     */
    public static String extractExtension(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
