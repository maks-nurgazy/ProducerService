package com.maksnurgazy.producerservice.services;


import com.maksnurgazy.producerservice.dto.DataDto;
import com.maksnurgazy.producerservice.dto.DataWrapperDto;

public interface FileHandlerService {
    DataWrapperDto saveToFile(DataDto dataDto);
}
