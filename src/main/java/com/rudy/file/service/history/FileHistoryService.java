package com.rudy.file.service.history;

import com.rudy.file.domain.FileHistory;
import com.rudy.file.dto.UploadDto;
import com.rudy.file.repository.FileHistoryRepository;
import com.rudy.file.response.FileHistoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileHistoryService {
    private final FileHistoryRepository fileHistoryRepository;

    /**
     * 파일 저장 히스토리를 조회합니다.
     */
    public Page<FileHistoryResponse> getFileHistory(Pageable pageable) {
        return fileHistoryRepository.findAll(pageable)
                .map(FileHistoryResponse::new);
    }

    /**
     * 파일 저장에 대한 히스토리 데이터를 저장합니다.
     */
    @Transactional
    public void addHistory(UploadDto request) {
        FileHistory fileHistory = new FileHistory(request);
        fileHistoryRepository.save(fileHistory);
        log.info("upload history success - {}", request.getFileOriginalName());
    }

    /**
     * 다수의 파일 저장에 대한 히스트리 데이터를 저장합니다
     */
    @Transactional
    public void addHistories(List<UploadDto> uploads) {
        for (UploadDto upload : uploads) {
            addHistory(upload);
        }
    }
}
