package com.rudy.file.response;

import com.rudy.file.domain.FileInfo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FileResponse {
    private final String fileName;
    private final String fileType;
    private final String filePath;
    private final LocalDateTime createDateTime;
    private final LocalDateTime updateDateTime;

    public FileResponse(FileInfo fileInfo) {
        this.fileName = fileInfo.getFileName();
        this.fileType = fileInfo.getFileType();
        this.filePath = fileInfo.getFilePath();
        this.createDateTime = fileInfo.getCreateDateTime();
        this.updateDateTime = fileInfo.getUpdateDateTime();
    }

    public FileResponse(String fileName, String fileType, String filePath, LocalDateTime createDateTime, LocalDateTime updateDateTime) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.filePath = filePath;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }
}
