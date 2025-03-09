package com.rudy.file.response;

import com.rudy.file.domain.FileHistory;
import com.rudy.file.domain.FileType;
import lombok.Getter;

@Getter
public class FileHistoryResponse {
    private final String username;
    private final String fileName;
    private final String filePath;
    private final FileType fileType;
    private final String fileSize;

    public FileHistoryResponse(FileHistory fileHistory) {
        this.username = fileHistory.getUsername();
        this.fileName = fileHistory.getFileName();
        this.filePath = fileHistory.getFilePath();
        this.fileType = fileHistory.getFileType();
        this.fileSize = fileHistory.getFileSize();
    }
}
