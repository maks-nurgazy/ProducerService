package com.maksnurgazy.producerservice.repositories;


import com.maksnurgazy.producerservice.entities.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedFileInfoRepository extends JpaRepository<FileInfo, String> {
}
