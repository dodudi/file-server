package com.rudy.file.service.upload;

import com.rudy.file.domain.FileInfo;
import com.rudy.file.domain.FileType;
import com.rudy.file.dto.UploadDto;
import com.rudy.file.repository.FileInfoRepository;
import com.rudy.file.util.Hasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleUploadService implements UploadService {
    private static final String BASE_PATH = "uploads";
    private final FileInfoRepository fileInfoRepository;
    private final Hasher hasher;

    @Value("${url.file-storage-server}")
    private String fileStorageServer;

    @Override
    @Transactional
    public UploadDto upload(MultipartFile file) {
        // 파일 저장 디렉토리 생성
        String fileOriginalFilename = getOriginalFilename(file);
        FileType fileType = FileType.determineFileType(fileOriginalFilename);
        Path uploadPath = getUploadPath(fileType);
        createDirectory(uploadPath);

        // 파일 이름 해싱, 파일 저장 이후 저장 경로 + 파일 이름 반환
        String fileHash = hasher.convertHash(fileOriginalFilename);
        String fileFullPath = saveFile(file, uploadPath.resolve(fileHash));

        // 서버 주소 + 저장 경로 + 해싱 파일 이름
        String fileUrl = fileStorageServer + "/" + fileFullPath;

        // DB 에 파일 정보 저장
        FileInfo fileInfo = new FileInfo(fileOriginalFilename, fileHash, fileType, fileFullPath, fileUrl, file.getSize());
        fileInfo = fileInfoRepository.save(fileInfo);
        log.debug("file upload info - {}", fileInfo);
        return new UploadDto(fileInfo);
    }

    @Override
    @Transactional
    public List<UploadDto> uploads(List<MultipartFile> files) {
        return files.stream()
                .map(this::upload)
                .toList();
    }

    /**
     * 파일 이름 반환 ( 확장자 포함 )
     */
    private String getOriginalFilename(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        return filename;
    }

    /**
     * 파일 저장 경로 생성
     */
    private Path getUploadPath(FileType fileType) {
        return Path.of(BASE_PATH, fileType.getFilePath());
    }

    /**
     * 파일 저장 후 저장 경로 + 파일 이름 반환
     */
    private String saveFile(MultipartFile file, Path filePath) {
        try {
            Files.write(filePath, file.getBytes());
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + filePath, e);
        }
    }


    /**
     * 파일 저장 디렉토리 생성
     */
    private void createDirectory(Path uploadPath) {
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory: " + uploadPath, e);
        }
    }

}
