package com.rudy.file.service.upload;

import com.rudy.file.dto.UploadDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UploadService {
    UploadDto upload(MultipartFile file);

    List<UploadDto> uploads(List<MultipartFile> files);
}
