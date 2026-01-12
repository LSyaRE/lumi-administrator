package com.luminesway.concursoadminstrator.modules.core.values;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DigitalOceanValues {

    public final String BUCKET_NAME;
    public final String REGION;
    public final String SPACES_KEY;
    public final String SPACES_SECRET;
    public final String URL_ORIGIN;

    public DigitalOceanValues(
            @Value("${file.storage.bucket}") String bucketName,
            @Value("${file.storage.region}") String region,
            @Value("${file.storage.origin.key}") String spacesKey,
            @Value("${file.storage.secret}") String spacesSecret,
            @Value("${file.storage.origin.url}") String urlOrigin
    ) {
        this.BUCKET_NAME = bucketName;
        this.REGION = region;
        this.SPACES_KEY = spacesKey;
        this.SPACES_SECRET = spacesSecret;
        this.URL_ORIGIN = urlOrigin;
    }



}

