package com.rudy.file.domain;

import com.rudy.file.dto.UploadDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private Long fileSize;

    public FileHistory(UploadDto request) {
        this.fileName = request.getFileOriginalName();
        this.filePath = request.getFileFullPath();
        this.fileType = request.getFileType();
        this.fileSize = request.getFileSize();
    }
}
