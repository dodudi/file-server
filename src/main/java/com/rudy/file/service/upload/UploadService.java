package com.rudy.file.service.upload;

import com.rudy.file.dto.UploadDto;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    UploadDto upload(MultipartFile file);
}
