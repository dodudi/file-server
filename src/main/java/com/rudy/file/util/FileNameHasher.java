package com.rudy.file.util;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileNameHasher {
    public String hashFileName(String originalFileName) {
        try {
            String extension = "";
            int dotIndex = originalFileName.lastIndexOf(".");
            if (dotIndex != -1) {
                extension = originalFileName.substring(dotIndex);
                originalFileName = originalFileName.substring(0, dotIndex);
            }

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String input = originalFileName + System.currentTimeMillis();
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString + extension;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }
}
