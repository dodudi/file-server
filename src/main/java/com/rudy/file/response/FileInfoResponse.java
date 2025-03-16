package com.rudy.file.response;

import com.rudy.file.domain.FileInfo;
import com.rudy.file.domain.FileType;
import lombok.Getter;

@Getter
public class FileInfoResponse {
    private final Long id;
    private final String fileOriginalName;
    private final String fileHashName;
    private final String fileFullPath;
    private final Long fileSize;
    private final FileType fileType;

    public FileInfoResponse(FileInfo fileInfo) {
        this.id = fileInfo.getId();
        this.fileOriginalName = fileInfo.getFileOriginalName();
        this.fileHashName = fileInfo.getFileHashName();
        this.fileFullPath = fileInfo.getFileFullPath();
        this.fileSize = fileInfo.getFileSize();
        this.fileType = fileInfo.getFileType();
    }
}
