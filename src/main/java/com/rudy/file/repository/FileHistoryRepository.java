package com.rudy.file.repository;

import com.rudy.file.domain.FileHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileHistoryRepository extends JpaRepository<FileHistory, Long> {
}
