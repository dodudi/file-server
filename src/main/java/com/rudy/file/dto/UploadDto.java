package com.rudy.file.dto;

import com.rudy.file.domain.FileInfo;
import com.rudy.file.domain.FileType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UploadDto {
    private final Long id;
    private final String fileOriginalName;
    private final String fileHashName;
    private final FileType fileType;
    private final String filePath;
    private final Long fileSize;

    private final LocalDateTime createDateTime;
    private final LocalDateTime updateDateTime;

    public UploadDto(FileInfo fileInfo) {
        this.id = fileInfo.getId();
        this.fileOriginalName = fileInfo.getFileOriginalName();
        this.fileHashName = fileInfo.getFileHashName();
        this.fileType = fileInfo.getFileType();
        this.filePath = fileInfo.getFileFullPath();
        this.fileSize = fileInfo.getFileSize();
        this.createDateTime = fileInfo.getCreateDateTime();
        this.updateDateTime = fileInfo.getUpdateDateTime();
    }
}
