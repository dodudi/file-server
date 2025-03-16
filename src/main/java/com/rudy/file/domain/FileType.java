package com.rudy.file.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum FileType {
    IMAGE("images", Set.of("jpg", "jpeg", "png", "gif", "bmp", "webp")),
    AUDIO("audios", Set.of("mp3", "wav", "aac", "ogg", "flac")),
    VIDEO("videos", Set.of("mp4", "mkv", "avi", "mov", "wmv")),
    DOCX("documents", Set.of("docx", "xls", "xlsx", "ppt", "pptx", "md")),
    LIB("libs", Set.of("zip", "rar")),
    OTHER("others", Set.of());

    private final String filePath;
    private final Set<String> extensions;

    FileType(String filePath, Set<String> extensions) {
        this.filePath = filePath;
        this.extensions = extensions;
    }

    private static final Map<String, FileType> EXTENSION_MAP = Arrays.stream(FileType.values())
            .flatMap(fileType -> fileType.extensions.stream().map(ext -> Map.entry(ext, fileType)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    public static FileType determineFileType(String fileOriginalName) {
        String extension = fileOriginalName.substring(fileOriginalName.lastIndexOf(".") + 1).toLowerCase();
        return EXTENSION_MAP.getOrDefault(extension, FileType.OTHER);
    }
}
