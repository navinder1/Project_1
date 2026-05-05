package com.project.tutorplatform.util;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static String generateFileName(MultipartFile file) {
        String ext = getExtension(file.getOriginalFilename());
        return UUID.randomUUID().toString() + "." + ext;
    }

    public static String getExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static boolean isImage(MultipartFile file) {
        return file.getContentType() != null &&
               file.getContentType().startsWith("image");
    }
}