package com.rudy.file.response;

import com.rudy.file.dto.UploadDto;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UploadResponse {
    private final Long id;
    private final String fileName;
    private final LocalDateTime createDateTime;
    private final LocalDateTime updateDateTime;

    public UploadResponse(UploadDto upload) {
        this.id = upload.getId();
        this.fileName = upload.getFileOriginalName();
        this.createDateTime = upload.getCreateDateTime();
        this.updateDateTime = upload.getUpdateDateTime();
    }
}
