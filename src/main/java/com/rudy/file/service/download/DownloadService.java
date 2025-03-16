package com.rudy.file.service.download;


import org.springframework.core.io.Resource;

public interface DownloadService {
    Resource download(Long id);
}
