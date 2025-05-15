package com.rudy.file.controller;

import com.rudy.file.dto.UploadDto;
import com.rudy.file.response.FileInfoResponse;
import com.rudy.file.response.UploadResponse;
import com.rudy.file.service.download.DownloadService;
import com.rudy.file.service.history.FileHistoryService;
import com.rudy.file.service.search.SimpleFileSearchService;
import com.rudy.file.service.upload.SimpleUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final SimpleUploadService uploadService;
    private final DownloadService downloadService;
    private final SimpleFileSearchService simpleFileSearchService;
    private final FileHistoryService fileHistoryService;

    @Operation(summary = "Get Save File Infos", description = "모든 저장된 파일 정보를 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<FileInfoResponse>> getFiles(@ParameterObject Pageable pageable) {
        Page<FileInfoResponse> files = simpleFileSearchService.getFiles(pageable);
        return ResponseEntity.ok(files);
    }

    @Operation(summary = "File Upload", description = "파일을 업로드합니다.")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadResponse> upload(@Valid @Parameter @RequestParam MultipartFile file) {
        UploadDto upload = uploadService.upload(file);
        fileHistoryService.addHistory(upload);

        UploadResponse uploadResponse = new UploadResponse(upload);
        return ResponseEntity.ok(uploadResponse);
    }

    @Operation(summary = "File Uploads", description = "여러 파일을 업로드합니다.")
    @PostMapping(value = "/uploads", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<UploadResponse>> upload(@Valid @Parameter @RequestParam List<MultipartFile> files) {
        List<UploadDto> uploads = uploadService.uploads(files);
        fileHistoryService.addHistories(uploads);
        return ResponseEntity.ok(uploads.stream()
                .map(UploadResponse::new)
                .toList()
        );
    }


    @Operation(summary = "File Download", description = "파일을 다운로드 합니다.")
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        Resource download = downloadService.download(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + download.getFilename() + "\"")
                .body(download);
    }
}
