package com.rudy.file.provider;

import com.rudy.file.domain.FileType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Component
public class FileProvider {
    @Value("${file.path.root}")
    private String rootPath;

    @Value("${file.path.image}")
    private String imagePath;

    @Value("${file.path.audio}")
    private String audioPath;

    @Value("${file.path.video}")
    private String videoPath;

    @Value("${file.path.document}")
    private String documentPath;

    @Value("${file.path.script}")
    private String scriptPath;

    @Value("${file.path.lib}")
    private String libPath;

    public Path getPath(FileType fileType) {
        if (fileType == null) {
            throw new IllegalArgumentException("FileType cannot be null");
        }

        String subPath = switch (fileType) {
            case IMAGE -> imagePath;
            case AUDIO -> audioPath;
            case VIDEO -> videoPath;
            case DOC, DOCX -> documentPath;
            case SCRIPT -> scriptPath;
            case LIB -> libPath;
        };

        return Path.of(rootPath, subPath);
    }

    public FileType determineFileType(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

        return switch (extension) {
            case "jpg", "jpeg", "png", "gif", "bmp", "webp" -> FileType.IMAGE;
            case "mp3", "wav", "aac", "ogg", "flac" -> FileType.AUDIO;
            case "mp4", "mkv", "avi", "mov", "wmv" -> FileType.VIDEO;
            case "pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "md" -> FileType.DOC;
            case "pyd" -> FileType.LIB;
            default -> throw new IllegalArgumentException("Unsupported file type: " + extension);
        };
    }
}
