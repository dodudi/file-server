package com.rudy.file.repository;

import com.rudy.file.domain.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
