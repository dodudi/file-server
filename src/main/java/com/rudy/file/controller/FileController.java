package com.rudy.file.controller;

import com.rudy.file.response.FileResponse;
import com.rudy.file.service.SimpleFileService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/upload")
    public ResponseEntity<List<FileResponse>> upload(
            @RequestPart(value = "files") MultipartFile[] files
    ) {
        return ResponseEntity.ok(fileService.uploadFiles(files));
    }

    @GetMapping
    public ResponseEntity<List<FileResponse>> getFiles(Pageable pageable) {
        return ResponseEntity.ok(fileService.getFiles(pageable));
    }
}
