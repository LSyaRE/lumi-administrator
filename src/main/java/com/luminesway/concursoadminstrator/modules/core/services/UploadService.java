package com.luminesway.concursoadminstrator.modules.core.services;

import com.luminesway.concursoadminstrator.shared.utils.Result;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    Result<?>  upload(MultipartFile file);
}
