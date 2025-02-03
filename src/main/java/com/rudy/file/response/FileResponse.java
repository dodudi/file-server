package com.rudy.file.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FileResponse {
    private final String fileName;
    private final String fileType;
    private final String filePath;
    private final LocalDateTime createDateTime;
    private final LocalDateTime updateDateTime;

    public FileResponse(String fileName, String fileType, String filePath, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}
