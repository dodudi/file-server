package com.rudy.file.request;

import lombok.Getter;

@Getter
public class FileRequest {
    private final String fileName;
    private final String filePath;
    private final String fileType;

    public FileRequest(String fileName, String filePath, String fileType) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
    }
}
