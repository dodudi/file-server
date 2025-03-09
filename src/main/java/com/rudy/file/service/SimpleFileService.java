package com.rudy.file.service;

import com.rudy.file.domain.FileInfo;
import com.rudy.file.domain.FileType;
import com.rudy.file.provider.FileProvider;
import com.rudy.file.repository.FileRepository;
import com.rudy.file.request.FileHistoryRequest;
import com.rudy.file.response.FileResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

    private final FileHistoryService fileHistoryService;

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

                    FileHistoryRequest fileHistoryRequest = new FileHistoryRequest("", response.getFileName(), response.getFilePath(), FileType.valueOf(response.getFileType()), "");
                    fileHistoryService.addUploadHistory(fileHistoryRequest);
                }
            }
        } catch (IOException e) {
            log.error("file upload error", e);
        }

        return uploadedFiles;
    }

    public FileResponse downloadFile(Long fileId, HttpServletResponse response) {
        FileInfo fileInfo = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("file not found"));
        String filePath = fileInfo.getFilePath();
        File targetFile = new File(filePath);

        if (!targetFile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return new FileResponse(fileInfo);
        }

//        Resource resource = new FileSystemResource(targetFile);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + targetFile.getName() + "\"");

        try (FileInputStream fis = new FileInputStream(targetFile); OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();

        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        return new FileResponse(fileInfo);
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
