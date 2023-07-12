package com.maksnurgazy.producerservice.repositories;


import com.maksnurgazy.producerservice.entities.ProcessedFileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedFileInfoRepository extends JpaRepository<ProcessedFileInfo, Long> {
}
