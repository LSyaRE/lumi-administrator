package com.luminesway.concursoadminstrator.modules.core.services.fileuploader;

import com.luminesway.concursoadminstrator.modules.core.values.DigitalOceanValues;
import com.luminesway.concursoadminstrator.modules.core.enums.FileType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.UUID;

@Component
public class DigitalOceanImpl implements FileUploader<Object> {

    public final S3Client s3Client;
    private final DigitalOceanValues digitalOceanValues;

    public DigitalOceanImpl(S3Client s3Client,  DigitalOceanValues digitalOceanValues) {
        this.s3Client = s3Client;
        this.digitalOceanValues = digitalOceanValues;
    }

    @Override
    public Object upload(MultipartFile file, FileType fileType) {
        try {

            String key = "uploads/" + UUID.randomUUID() + "-" + file.getOriginalFilename();


            // private or public-read
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(digitalOceanValues.BUCKET_NAME)
                    .key(key)
                    .contentType(file.getContentType())
                    .acl("public-read")
                    .build();
            s3Client.putObject(
                    request,
                    RequestBody.fromInputStream(
                            file.getInputStream(),
                            file.getSize()
                    )
            );

            return  "https://"+ digitalOceanValues.BUCKET_NAME+"."+ digitalOceanValues.REGION+".cdn.digitaloceanspaces.com/" + key ;

        } catch (NoSuchKeyException e) {
            System.out.println("No se encontró el archivo");
        } catch (S3Exception e) {
            System.out.println("Error al subir: " + e.awsErrorDetails().errorMessage());
        } catch (IOException e) {
            System.out.println("Error al subir: " + e);

        }
       return null;
    }
}
