package com.rudy.file.service;

import com.rudy.file.domain.FileType;
import com.rudy.file.provider.FileProvider;
import com.rudy.file.repository.FileRepository;
import com.rudy.file.request.FileRequest;
import com.rudy.file.response.FileResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

                    LocalDateTime localDateTime = LocalDateTime.now();
                    FileResponse response = new FileResponse(fileName, fileType.toString(), filePath.toString(), localDateTime, localDateTime);
                    uploadedFiles.add(response);
                }
            }
        } catch (IOException e) {
            log.error("File upload error", e);
        }

        return uploadedFiles;
    }

    public void downloadFile(String fileName, HttpServletResponse response) {
        try {
            Path filePath = Paths.get("uploads/").resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("File not found: " + fileName);
                return;
            }

            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            // 응답 헤더 설정
            response.setContentType(mimeType);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()));

            // 파일 데이터 전송
            Files.copy(filePath, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (MalformedURLException e) {
            log.error("simple download malformed url error", e);
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Invalid file path: " + fileName);
            } catch (IOException ioException) {
                log.error("simple download malformed url error", e);
            }
        } catch (IOException e) {
            log.error("simple download io error", e);
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Error reading file: " + fileName);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
