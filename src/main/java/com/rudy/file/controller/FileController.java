package com.rudy.file.controller;

import com.rudy.file.request.FileRequest;
import com.rudy.file.response.FileResponse;
import com.rudy.file.service.SimpleFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final SimpleFileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<List<FileResponse>> upload(
            @RequestPart(value = "files") MultipartFile[] files
    ) {
        return ResponseEntity.ok(fileService.uploadFiles(files));
    }
}
