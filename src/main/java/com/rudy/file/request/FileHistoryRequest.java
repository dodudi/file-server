package com.rudy.file.request;

import com.rudy.file.domain.FileType;
import lombok.Getter;

@Getter
public class FileHistoryRequest {
    private final String username;
    private final String fileName;
    private final String filePath;
    private final FileType fileType;
    private final String fileSize;

    public FileHistoryRequest(String username, String fileName, String filePath, FileType fileType, String fileSize) {
        this.username = username;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
