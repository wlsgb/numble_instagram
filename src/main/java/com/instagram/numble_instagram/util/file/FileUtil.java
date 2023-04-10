package com.instagram.numble_instagram.util.file;

public class FileUtil {
    public static String extractExtension(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
