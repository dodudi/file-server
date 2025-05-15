package com.rudy.file.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileOriginalName;
    private String fileHashName;
    private String fileFullPath;
    private String fileUrl;
    private Long fileSize;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    public FileInfo(String fileOriginalName, String fileHashName, FileType fileType, String fileFullPath, String fileUrl, Long fileSize) {
        this.fileOriginalName = fileOriginalName;
        this.fileHashName = fileHashName;
        this.fileType = fileType;
        this.fileFullPath = fileFullPath;
        this.fileUrl = fileUrl;
        this.fileSize = fileSize;
    }
}
