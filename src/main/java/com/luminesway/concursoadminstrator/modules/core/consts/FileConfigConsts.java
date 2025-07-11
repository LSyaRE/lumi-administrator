package com.luminesway.concursoadminstrator.modules.core.consts;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class FileConfigConsts {
    @Value("${file.core.upload.uri}")
    private   String uploadUri;
    @Value("${file.core.upload.path}")
    private  String uploadPath;
    @Value("${file.core.upload.header}")
    private  String uploadHeader;
    @Value("${file.core.upload.secret}")
    private String uploadSecret;
}
