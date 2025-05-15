package com.rudy.file.controller;

import com.rudy.file.dto.UploadDto;
import com.rudy.file.response.UploadResponse;
import com.rudy.file.service.history.FileHistoryService;
import com.rudy.file.service.upload.BlogUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files/blog")
public class BlogFileController {
    private final BlogUploadService blogUploadService;
    private final FileHistoryService fileHistoryService;

    @Operation(summary = "File Upload", description = "파일을 업로드합니다.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> upload(@Valid @Parameter @RequestParam MultipartFile file) {
        UploadDto upload = blogUploadService.upload(file);
        fileHistoryService.addHistory(upload);
        UploadResponse uploadResponse = new UploadResponse(upload);
        return ResponseEntity.ok(uploadResponse);
    }

    @Operation(summary = "File Uploads", description = "여러 파일을 업로드합니다.")
    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<UploadResponse>> upload(@Valid @Parameter @RequestParam List<MultipartFile> files) {
        List<UploadDto> uploads = blogUploadService.uploads(files);
        fileHistoryService.addHistories(uploads);
        return ResponseEntity.ok(uploads.stream()
                .map(UploadResponse::new)
                .toList()
        );
    }
}
