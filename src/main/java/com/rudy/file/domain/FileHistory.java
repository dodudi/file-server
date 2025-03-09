package com.rudy.file.domain;

import com.rudy.file.request.FileHistoryRequest;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class FileHistory extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String fileName;
    private String filePath;

    @Enumerated(EnumType.STRING)
    private FileType fileType;
    private String fileSize;

    public FileHistory(FileHistoryRequest request) {
        this.username = request.getUsername();
        this.fileName = request.getFileName();
        this.filePath = request.getFilePath();
        this.fileType = request.getFileType();
        this.fileSize = request.getFileSize();
    }
}
