package com.maksnurgazy.producerservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maksnurgazy.producerservice.dto.DataDto;
import com.maksnurgazy.producerservice.dto.DataWrapperDto;
import com.maksnurgazy.producerservice.entities.FileInfo;
import com.maksnurgazy.producerservice.enums.ProcessStatus;
import com.maksnurgazy.producerservice.repositories.ProcessedFileInfoRepository;
import com.maksnurgazy.producerservice.services.FileHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileHandlerServiceImpl implements FileHandlerService {
    private static final String RECORDS_DIRECTORY = "./../ProcessingService/logs/records/";

    private final SimpleDateFormat dateFormat;
    private final ObjectMapper objectMapper;
    private final ProcessedFileInfoRepository processedFileInfoRepository;


    @Override
    public DataWrapperDto saveToFile(DataDto dataDto) {
        String fileName = getFileName(dataDto);
        String filePath = RECORDS_DIRECTORY + fileName;
        Path path = Path.of(filePath);

        try {
            boolean fileExists = Files.exists(path);
            String line = objectMapper.writeValueAsString(dataDto);

            if (!fileExists) {
                Files.createFile(path);
                writeRecordCount(filePath, 0);
            }

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            int recordCount = getRecordCount(filePath) + 1;
            writeRecordCount(filePath, recordCount);

            long fileLength = file.length();
            file.seek(fileLength);
            file.writeBytes(line + System.lineSeparator());
            file.close();

            System.out.println(fileName);

            FileInfo fileInfo = processedFileInfoRepository.findById(fileName)
                    .map(existingFileInfo -> {
                        existingFileInfo.setStatus(ProcessStatus.UPDATED);

                        return existingFileInfo;
                    })
                    .orElse(FileInfo.builder()
                            .id(fileName)
                            .status(ProcessStatus.NEW)
                            .fileName(fileName)
                            .lastProcessedRecord(0)
                            .lastFileNumber(0)
                            .build());

            writeToDatabase(fileInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DataWrapperDto(dataDto);
    }

    private String getFileName(DataDto dataDto) {
        return String.format("%s-%s.log", dataDto.getServiceType().getValue(),
                dateFormat.format(dataDto.getCreation().getDate()));
    }

    private int getRecordCount(String filePath) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            String line = file.readLine();

            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void writeRecordCount(String filePath, int recordCount) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "rw")) {
            file.seek(0);
            file.writeBytes(recordCount + System.lineSeparator());
        }
    }

    private void writeToDatabase(FileInfo fileInfo) {
        processedFileInfoRepository.save(fileInfo);
    }
}
