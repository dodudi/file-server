package com.rudy.file.service.history;

import com.rudy.file.domain.FileHistory;
import com.rudy.file.dto.UploadDto;
import com.rudy.file.repository.FileHistoryRepository;
import com.rudy.file.response.FileHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileHistoryService {
    private final FileHistoryRepository fileHistoryRepository;

    public Page<FileHistoryResponse> getFileHistory(Pageable pageable) {
        return fileHistoryRepository.findAll(pageable)
                .map(FileHistoryResponse::new);
    }

    @Transactional
    public void addHistory(UploadDto request) {
        FileHistory fileHistory = new FileHistory(request);
        fileHistoryRepository.save(fileHistory);
        log.info("upload history success - {}", request.getFileOriginalName());
    }
}
