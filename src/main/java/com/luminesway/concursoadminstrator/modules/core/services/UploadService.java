package com.luminesway.concursoadminstrator.modules.core.services;

import com.luminesway.concursoadminstrator.modules.core.enums.FileType;
import com.luminesway.concursoadminstrator.shared.utils.SpringResult;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    /**
     * Uploads a file of a specified file type.
     *
     * @param file the multipart file to be uploaded
     * @param fileType the type of the file being uploaded (e.g., IMAGE, AUDIO, VIDEO, DOCUMENT)
     * @return a Result instance containing the upload outcome, with details such as status code,
     *         any errors encountered, or successful result data
     */
    SpringResult<?> upload(MultipartFile file, FileType fileType);
}
