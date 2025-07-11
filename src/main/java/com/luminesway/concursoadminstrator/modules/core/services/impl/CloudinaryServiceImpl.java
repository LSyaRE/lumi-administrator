package com.luminesway.concursoadminstrator.modules.core.services.impl;

import com.luminesway.concursoadminstrator.modules.core.consts.FileConfigConsts;
import com.luminesway.concursoadminstrator.modules.core.dtos.CloudinaryResDto;
import com.luminesway.concursoadminstrator.modules.core.enums.FileType;
import com.luminesway.concursoadminstrator.modules.core.services.UploadService;
import com.luminesway.concursoadminstrator.shared.utils.Result;
import com.luminesway.concursoadminstrator.shared.utils.ResultParameters;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Objects;

/**
 * Cloudinary methods that helps the uses
 *
 * @author LSyaRE <mailto:javillacis@luminesway.com>
 * @version 1.0
 * @since 04/07/2025
 */
@Service
@Log4j2
public class CloudinaryServiceImpl implements UploadService {
    private final WebClient webClient;
    private final FileConfigConsts fileConfigConsts;

    public CloudinaryServiceImpl(WebClient webClient, FileConfigConsts fileConfigConsts) {
        this.webClient = webClient;
        this.fileConfigConsts = fileConfigConsts;
    }

    @Override
    public Result<?> upload(MultipartFile file) {
        try {
            MultipartBodyBuilder builder = new MultipartBodyBuilder();

            log.info("Creating resource");
            ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            log.info("Creating MultipartBodyBuilder");
            builder.part("type", FileType.AUDIO.name());
            builder.part("file", resource)
                    .filename(Objects.requireNonNull(file.getOriginalFilename()))
                    .contentType(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())));

            MultiValueMap<String, HttpEntity<?>> multipartData = builder.build();

            log.info("Sending MultipartBodyBuilder");
            CloudinaryResDto response = webClient.post()
                    .uri(fileConfigConsts.getUploadUri().concat(fileConfigConsts.getUploadPath()))
                    .header(fileConfigConsts.getUploadHeader(), fileConfigConsts.getUploadSecret())
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(multipartData)
                    .retrieve()
                    .bodyToMono(CloudinaryResDto.class)
                    .block();

            return Result.success(ResultParameters.<CloudinaryResDto>builder().message("Archivo subido correctamente").result(response).build(), 201);
        }
        catch (IOException e) {
            log.error(e.getMessage());
            return Result.error(ResultParameters.builder().message("Algo ha ocurrido en la subida del archivo").build(), 500);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(ResultParameters.builder().message(e.getMessage()).build(), 500);
        }
    }
}
