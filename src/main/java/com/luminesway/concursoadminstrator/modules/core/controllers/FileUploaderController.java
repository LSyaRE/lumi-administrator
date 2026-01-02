package com.luminesway.concursoadminstrator.modules.core.controllers;

import com.luminesway.concursoadminstrator.modules.core.enums.FileType;
import com.luminesway.concursoadminstrator.modules.core.services.UploadService;
import com.luminesway.concursoadminstrator.modules.core.services.impl.CloudinaryServiceImpl;
import com.luminesway.concursoadminstrator.shared.utils.SpringResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    /**
     * Handles the upload of a sound file. This method processes a multipart file upload request
     * and uploads the provided sound file to the appropriate service. The request must include
     * the file and the specified file type.
     *
     * @param file the multipart file to be uploaded
     * @param fileType the type of the file being uploaded (e.g., AUDIO)
     * @return ResponseEntity containing the result of the upload operation and corresponding HTTP status
     */
    @PostMapping(path = "/sound", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PreAuthorize("hasAnyRole(@roles.ADMIN)")
    public ResponseEntity<?> uploadSound(@RequestParam("file") MultipartFile file,
                                         @RequestParam("fileType") FileType fileType) {
        log.info("Uploading sound file");
        log.info(file.getOriginalFilename());
        SpringResult<?> result =  fileUploaderService.upload(file, fileType);
        log.info(result.toString());
        return ResponseEntity.status(result.getCode()).body(result.toJson());
    }
}
