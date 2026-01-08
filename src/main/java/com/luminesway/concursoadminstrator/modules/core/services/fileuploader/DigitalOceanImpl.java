package com.luminesway.concursoadminstrator.modules.core.services.fileuploader;

import com.luminesway.concursoadminstrator.modules.core.enums.FileType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class DigitalOceanImpl implements FileUploader<Object> {
    @Override
    public Object upload(MultipartFile file, FileType fileType) {
        return null;
    }
}
