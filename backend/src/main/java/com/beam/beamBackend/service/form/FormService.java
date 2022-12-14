package com.beam.beamBackend.service.form;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beam.beamBackend.enums.FormEnum;
import com.beam.beamBackend.model.Form;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IFormRepository;
import com.beam.beamBackend.service.form.decorator.Clear;
import com.beam.beamBackend.service.form.decorator.FileWrapper;
import com.beam.beamBackend.service.form.decorator.MultipartFileWrapper;
import com.beam.beamBackend.service.form.decorator.Postfix;
import com.beam.beamBackend.service.form.decorator.Prefix;
import com.beam.beamBackend.service.form.decorator.UniquelyNameable;

import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import com.beam.beamBackend.model.User;
import java.util.Optional;



@Service
@RequiredArgsConstructor
public class FormService {
    final long ONE_KB = 1024;
    final long ONE_MB = ONE_KB * ONE_KB; 
    final long FILE_SIZE_LIMIT = ONE_MB;

    private final String DEFAULT_BUCKET_NAME = "beam-form-bucket";
    private final IFormRepository formRepository;
    private final IAccountRepository accountRepository;

    public boolean uploadForm(MultipartFile file, UUID userUuid, FormEnum formType) throws IOException, FileSizeLimitExceededException, UsernameNotFoundException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        if (file.getSize() > FILE_SIZE_LIMIT) {
            String errMsg = "Exceeded the file size limit of " + FILE_SIZE_LIMIT;
            throw new FileSizeLimitExceededException(errMsg, file.getSize(), FILE_SIZE_LIMIT);
        }

        Optional<User> user = accountRepository.findById(userUuid);
        if (user.isPresent()) {
            User u = user.get();
            String bilkentId = Long.toString(u.getBilkentId());

            // Generate key using file specs
            MultipartFileWrapper fileWrapper = new MultipartFileWrapper(file);
            UniquelyNameable nameable = new Clear(fileWrapper);
            nameable = new Prefix(nameable, bilkentId + "_");
            nameable = new Prefix(nameable, formType.toString() + "_");
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
                return false;
            }
        }
        else {
            throw new UsernameNotFoundException("User with given ID could not be found.");
        }
    }

    public boolean uploadForm(File file, UUID userUuid, FormEnum formType) throws FileSizeLimitExceededException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        if (file == null || file.length() == 0) {
            throw new NullPointerException("File field cannot be empty");
        }
        if (file.length() > FILE_SIZE_LIMIT) {
            String errMsg = "Exceeded the file size limit of " + FILE_SIZE_LIMIT;
            throw new FileSizeLimitExceededException(errMsg, file.length(), FILE_SIZE_LIMIT);
        }

        Optional<User> user = accountRepository.findById(userUuid);
        if (user.isPresent()) {
            User u = user.get();
            String bilkentId = Long.toString(u.getBilkentId());
            
            FileWrapper fileWrapper = new FileWrapper(file);
            UniquelyNameable nameable = new Clear(fileWrapper);
            nameable = new Prefix(nameable, bilkentId + "_");
            nameable = new Prefix(nameable, formType.toString() + "_");
            nameable = new Postfix(nameable, timestamp);
            nameable = new Postfix(nameable, ".pdf");

            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key("key")
                    .build();
            s3.putObject(objectRequest, RequestBody.fromFile(file));
            return true;
        }
        else {
            throw new UsernameNotFoundException("User with given ID could not be found.");
        }
    }

    public byte[] downloadForm(UUID userUuid, FormEnum formType) throws IOException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        
        final String key = formRepository.findFormByIdAndFormType(userUuid, formType).getKey();

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        return s3.getObject(getObjectRequest).readAllBytes();
    }

    public boolean deleteFile(UUID userUuid, FormEnum formType) {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        
        final String key = formRepository.findFormByIdAndFormType(userUuid, formType).getKey();

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();
        s3.deleteObject(deleteObjectRequest);
        return true;
    }
}
