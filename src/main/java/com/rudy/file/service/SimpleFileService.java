package com.rudy.file.service;

import com.rudy.file.domain.FileInfo;
import com.rudy.file.domain.FileType;
import com.rudy.file.provider.FileProvider;
import com.rudy.file.repository.FileRepository;
import com.rudy.file.response.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SimpleFileService {
    private final FileProvider fileProvider;
    private final FileRepository fileRepository;

    public List<FileResponse> uploadFiles(MultipartFile[] files) {
        List<FileResponse> uploadedFiles = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    assert fileName != null;

                    FileType fileType = fileProvider.determineFileType(fileName);
                    Path uploadPath = fileProvider.getPath(fileType);
                    if (!Files.exists(uploadPath)) {
                        Files.createDirectories(uploadPath);
                    }

                    Path filePath = uploadPath.resolve(fileName);
                    Files.write(filePath, file.getBytes());

                    FileInfo fileInfo = new FileInfo(fileName, fileType.toString(), filePath.toString());
                    fileInfo = fileRepository.save(fileInfo);
                    log.debug("file upload info - {}", fileInfo);

                    FileResponse response = new FileResponse(fileInfo);
                    uploadedFiles.add(response);
                }
            }
        } catch (IOException e) {
            log.error("file upload error", e);
        }

        return uploadedFiles;
    }

    public List<FileResponse> getFiles(Pageable pageable) {
        Page<FileInfo> pageInfos = fileRepository.findAll(pageable);
        List<FileInfo> items = pageInfos.getContent();
        return items.stream().map(FileResponse::new).toList();
    }

    public void deleteFile(Long id) {
        fileRepository.findById(id).ifPresent(f -> {
            Path fileFullPath = Path.of(f.getFilePath());

            try {
                Files.deleteIfExists(fileFullPath);
                fileRepository.delete(f);
            } catch (Exception e) {
                log.error("", e);
            }
        });
    }
}
