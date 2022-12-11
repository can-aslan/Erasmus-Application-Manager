package com.beam.beamBackend.aws;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class FileService {
    final String PREAPPROVAL_BUCKET_NAME = "beam-form-bucket";

    public void uploadFile(MultipartFile file, String studentId) throws IOException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = PREAPPROVAL_BUCKET_NAME;
        
        // TODO: Add decorator pattern for naming files. prefix file type, postfix file type and date
        String filename = file.getOriginalFilename();
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());
        final String key = filename + timestamp;

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
    }
    
    
}
