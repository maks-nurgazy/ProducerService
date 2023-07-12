package com.maksnurgazy.producerservice.entities;

import com.maksnurgazy.producerservice.enums.ProcessStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ProcessedFileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private Integer lastProcessedRecord;
    @Enumerated(EnumType.STRING)
    private ProcessStatus status;
}