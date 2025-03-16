package com.rudy.file.service.search;

import com.rudy.file.repository.FileInfoRepository;
import com.rudy.file.response.FileInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleFileSearchService {
    private final FileInfoRepository fileInfoRepository;

    public Page<FileInfoResponse> getFiles(Pageable pageable) {
        return fileInfoRepository.findAll(pageable)
                .map(FileInfoResponse::new);
    }
}
