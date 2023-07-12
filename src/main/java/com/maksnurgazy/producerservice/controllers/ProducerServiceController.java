package com.maksnurgazy.producerservice.controllers;

import com.maksnurgazy.producerservice.dto.DataDto;
import com.maksnurgazy.producerservice.dto.DataWrapperDto;
import com.maksnurgazy.producerservice.services.FileHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/services")
public class ProducerServiceController {
    private final FileHandlerService fileHandlerService;

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public DataWrapperDto saveToFile(@RequestBody DataDto dataDto){
        return fileHandlerService.saveToFile(dataDto);
    }
}
