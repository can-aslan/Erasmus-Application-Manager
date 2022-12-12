package com.beam.beamBackend.service.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beam.beamBackend.enums.FileEnum;
import com.beam.beamBackend.service.file.decorator.Clear;
import com.beam.beamBackend.service.file.decorator.MultipartFileWrapper;
import com.beam.beamBackend.service.file.decorator.Postfix;
import com.beam.beamBackend.service.file.decorator.Prefix;
import com.beam.beamBackend.service.file.decorator.UniquelyNameable;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
@RequiredArgsConstructor
public class FileService {
    final String BUCKET_NAME = "beam-form-bucket";

    public boolean uploadFile(MultipartFile file, String bilkentStudentId, FileEnum fileType) throws IOException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = BUCKET_NAME;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());
        
        // Generate key using file specs
        MultipartFileWrapper fileWrapper = new MultipartFileWrapper(file);
        UniquelyNameable nameable = new Clear(fileWrapper);
        nameable = new Prefix(nameable, bilkentStudentId + "_");
        nameable = new Prefix(nameable, fileType.toString() + "_");
        nameable = new Postfix(nameable, timestamp);
        nameable = new Postfix(nameable, ".pdf");

        try {
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(nameable.getUniqueName())
                    .build();
            s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResponseInputStream<GetObjectResponse> downloadFile(String fileKey) {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = BUCKET_NAME;

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();

        // TODO: Don't know what should I return so that frontend can download the file
        return s3.getObject(getObjectRequest);
    }

    public boolean deleteFile(String fileKey) {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = BUCKET_NAME;
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileKey)
                .build();
            s3.deleteObject(deleteObjectRequest);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
