package com.rudy.file.response;

import com.rudy.file.dto.UploadDto;
import lombok.Getter;

import java.time.Instant;

@Getter
public class UploadResponse {
    private final Long id;
    private final String fileName;
    private final String fileUrl;
    private final Instant createDateTime;
    private final Instant updateDateTime;

    public UploadResponse(UploadDto upload) {
        this.id = upload.getId();
        this.fileName = upload.getFileOriginalName();
        this.fileUrl = upload.getFileUrl();
        this.createDateTime = upload.getCreateDateTime();
        this.updateDateTime = upload.getUpdateDateTime();
    }
}
