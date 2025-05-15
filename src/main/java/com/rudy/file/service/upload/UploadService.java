package com.rudy.file.service.upload;

import com.rudy.file.dto.UploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {
    /**
     * 단일 파일 업로드
     */
    UploadDto upload(MultipartFile file);

    /**
     * 다수의 파일 업로드
     */
    List<UploadDto> uploads(List<MultipartFile> files);
}
