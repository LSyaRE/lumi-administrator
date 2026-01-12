package com.luminesway.concursoadminstrator.modules.core.services.fileuploader;

import com.luminesway.concursoadminstrator.modules.core.enums.FileType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileUploader<T> {
    public T upload(MultipartFile file, FileType fileType);
}
