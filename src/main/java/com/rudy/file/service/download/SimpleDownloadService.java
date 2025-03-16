package com.rudy.file.service.download;

import com.rudy.file.repository.FileInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class SimpleDownloadService implements DownloadService {

    private final FileInfoRepository fileInfoRepository;

    @Override
    public Resource download(Long id) {
        return fileInfoRepository.findById(id)
                .map(fileInfo -> {
                    try {
                        Path filePath = Paths.get(fileInfo.getFileFullPath()).toAbsolutePath().normalize();
                        Resource resource = new UrlResource(filePath.toUri());

                        if (resource.exists() && resource.isReadable()) {
                            return resource;
                        } else {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다.");
                        }
                    } catch (MalformedURLException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일을 읽을 수 없습니다.", e);
                    }
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 ID의 파일이 존재하지 않습니다."));
    }
}
