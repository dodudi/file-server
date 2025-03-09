package com.rudy.file.service;

import com.rudy.file.domain.FileHistory;
import com.rudy.file.repository.FileHistoryRepository;
import com.rudy.file.request.FileHistoryRequest;
import com.rudy.file.response.FileHistoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileHistoryService {
    private final FileHistoryRepository fileHistoryRepository;

    @Transactional
    public FileHistoryResponse addUploadHistory(FileHistoryRequest request) {
        FileHistory fileHistory = new FileHistory(request);
        return new FileHistoryResponse(fileHistoryRepository.save(fileHistory));
    }
}
