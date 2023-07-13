package com.maksnurgazy.producerservice.entities;

import com.maksnurgazy.producerservice.enums.ProcessStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    @Id
    private String id;
    private String fileName;
    private Integer lastProcessedRecord;
    private Integer lastFileNumber;
    @Enumerated(EnumType.STRING)
    private ProcessStatus status;

    public String getNextFileName() {
        if (lastProcessedRecord - lastFileNumber * 100 >= 100) {
            lastFileNumber++;
        }
        String suffix = String.format("%04d", lastFileNumber);
        String prefix = fileName.split("\\.")[0];

        return prefix + "-" + suffix + ".log";
    }
}