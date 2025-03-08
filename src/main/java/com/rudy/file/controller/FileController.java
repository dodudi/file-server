package com.rudy.file.controller;

import com.rudy.file.response.FileResponse;
import com.rudy.file.service.SimpleFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {
    private final SimpleFileService fileService;

    @Operation(summary = "Multiple File Upload", description = "여러 개의 파일을 업로드합니다.")
    @PostMapping("/upload")
    public ResponseEntity<List<FileResponse>> upload(
            @io.swagger.v3.oas.annotations.parameters.RequestBody @RequestParam @RequestPart(value = "files") MultipartFile[] files
    ) {
        return ResponseEntity.ok(fileService.uploadFiles(files));
    }

    @GetMapping("/download")
    public ResponseEntity<FileResponse> download(@RequestParam Long fileId, HttpServletResponse response) {
        FileResponse fileResponse = fileService.downloadFile(fileId, response);
        return ResponseEntity.ok(fileResponse);
    }

    @GetMapping
    public ResponseEntity<List<FileResponse>> getFiles(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(fileService.getFiles(pageable));
    }

    @DeleteMapping
    public ResponseEntity<FileResponse> delete(@RequestParam("id") Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.ok().build();
    }
}
