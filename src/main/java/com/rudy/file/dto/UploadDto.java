package com.rudy.file.dto;

import com.rudy.file.domain.FileInfo;
import com.rudy.file.domain.FileType;
import lombok.Getter;

import java.time.Instant;

@Getter
public class UploadDto {
    private final Long id;
    private final String fileOriginalName;
    private final String fileHashName;
    private final FileType fileType;
    private final String fileFullPath;
    private final String fileUrl;
    private final Long fileSize;
    private final Instant createDateTime;
    private final Instant updateDateTime;

    public UploadDto(FileInfo fileInfo) {
        this.id = fileInfo.getId();
        this.fileOriginalName = fileInfo.getFileOriginalName();
        this.fileHashName = fileInfo.getFileHashName();
        this.fileType = fileInfo.getFileType();
        this.fileFullPath = fileInfo.getFileFullPath();
        this.fileUrl = fileInfo.getFileUrl();
        this.fileSize = fileInfo.getFileSize();
        this.createDateTime = fileInfo.getCreateDateTime();
        this.updateDateTime = fileInfo.getUpdateDateTime();
    }
}
