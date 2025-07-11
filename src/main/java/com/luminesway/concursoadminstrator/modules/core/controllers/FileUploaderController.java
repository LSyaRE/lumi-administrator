package com.luminesway.concursoadminstrator.modules.core.controllers;

import com.luminesway.concursoadminstrator.modules.core.services.UploadService;
import com.luminesway.concursoadminstrator.modules.core.services.impl.CloudinaryServiceImpl;
import com.luminesway.concursoadminstrator.shared.utils.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/upload")
@Log4j2
public class FileUploaderController {
    private final UploadService fileUploaderService;

    public FileUploaderController(CloudinaryServiceImpl fileUploaderService) {
        this.fileUploaderService = fileUploaderService;
    }

    @PostMapping(path = "/sound", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyRole(@roles.ADMIN)")
    public ResponseEntity<?> uploadSound(@RequestPart("file") MultipartFile file) {
        log.info("Uploading sound file");
        log.info(file.getOriginalFilename());
        Result<?> result =  fileUploaderService.upload(file);
        log.info(result.toString());
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }
}
