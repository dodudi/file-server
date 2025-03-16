package com.rudy.file.controller;

import com.rudy.file.response.FileHistoryResponse;
import com.rudy.file.service.history.FileHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "File History API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/files/history")
public class FileHistoryController {
    private final FileHistoryService fileHistoryService;

    @Operation(summary = "Get Save File Histories", description = "모든 저장된 파일 이력을 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<FileHistoryResponse>> getFileHistory(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(fileHistoryService.getFileHistory(pageable));
    }
}
