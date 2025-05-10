package com.rudy.file.service.upload;

import com.rudy.file.domain.FileInfo;
import com.rudy.file.domain.FileType;
import com.rudy.file.dto.UploadDto;
import com.rudy.file.repository.FileInfoRepository;
import com.rudy.file.util.FileNameHasher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private static final String BASE_PATH = "upload";
    private final FileInfoRepository fileInfoRepository;
    private final FileNameHasher fileNameHasher;

    @Transactional
    public UploadDto upload(MultipartFile file) {
        String fileOriginalFilename = getOriginalFilename(file);
        FileType fileType = FileType.determineFileType(fileOriginalFilename);
        Path uploadPath = getUploadPath(fileType);
        createDirectory(uploadPath);

        String fileHash = fileNameHasher.hashFileName(fileOriginalFilename);
        String fileFullPath = saveFile(file, uploadPath.resolve(fileHash));

        FileInfo fileInfo = new FileInfo(fileOriginalFilename, fileHash, fileType, fileFullPath, file.getSize());
        fileInfo = fileInfoRepository.save(fileInfo);
        log.debug("file upload info - {}", fileInfo);
        return new UploadDto(fileInfo);
    }

    @Override
    public List<UploadDto> uploads(List<MultipartFile> files) {
        return files.stream()
                .map(this::upload)
                .toList();
    }

    private String getOriginalFilename(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isBlank()) {
            throw new IllegalArgumentException("File name cannot be null or empty");
        }
        return filename;
    }

    private Path getUploadPath(FileType fileType) {
        return Path.of(BASE_PATH, fileType.getFilePath());
    }

    private String saveFile(MultipartFile file, Path filePath) {
        try {
            Files.write(filePath, file.getBytes());
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + filePath, e);
        }
    }

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
