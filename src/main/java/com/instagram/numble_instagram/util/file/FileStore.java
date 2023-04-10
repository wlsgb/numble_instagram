package com.instagram.numble_instagram.util.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileStore {
    String uploadFile(MultipartFile file);

    void deleteFile(String fileUrl);
}
