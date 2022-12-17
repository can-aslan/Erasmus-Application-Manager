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
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
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

    public boolean uploadForm(MultipartFile file, UUID userId, FormEnum formType) throws IOException, FileSizeLimitExceededException, UsernameNotFoundException, Exception {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());
        
        // TODO: Check userId
        Optional<Form> form = formRepository.findFormByUserIdAndFormType(userId, formType);
        if (form.isPresent()) {
            throw new Exception("A form with given form type is already saved in the system.");
        }

        if (file.getSize() > FILE_SIZE_LIMIT) {
            String errMsg = "Exceeded the file size limit of " + FILE_SIZE_LIMIT;
            throw new FileSizeLimitExceededException(errMsg, file.getSize(), FILE_SIZE_LIMIT);
        }

        Optional<User> user = accountRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with given ID could not be found.");
        }

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
            final String key = nameable.getUniqueName();
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            final UUID id = UUID.randomUUID();
            Form newForm = new Form(id, userId, formType, key);
            formRepository.save(newForm);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public boolean uploadForm(File file, UUID userId, FormEnum formType) throws FileSizeLimitExceededException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());

        // TODO: Check userId

        if (file == null || file.length() == 0) {
            throw new NullPointerException("File field cannot be empty");
        }
        if (file.length() > FILE_SIZE_LIMIT) {
            String errMsg = "Exceeded the file size limit of " + FILE_SIZE_LIMIT;
            throw new FileSizeLimitExceededException(errMsg, file.length(), FILE_SIZE_LIMIT);
        }

        Optional<User> user = accountRepository.findById(userId);
        if (!user.isPresent()) {
            throw new UsernameNotFoundException("User with given ID could not be found.");
        }
        
        User u = user.get();
        String bilkentId = Long.toString(u.getBilkentId());
        
        FileWrapper fileWrapper = new FileWrapper(file);
        UniquelyNameable nameable = new Clear(fileWrapper);
        nameable = new Prefix(nameable, bilkentId + "_");
        nameable = new Prefix(nameable, formType.toString() + "_");
        nameable = new Postfix(nameable, timestamp);
        nameable = new Postfix(nameable, ".pdf");

        final String key = nameable.getUniqueName();
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(objectRequest, RequestBody.fromFile(file));

        final UUID id = UUID.randomUUID();
        Form form = new Form(id, userId, formType, key);
        formRepository.save(form);

        return true;
    }

    public byte[] downloadForm(UUID userId, FormEnum formType) throws IOException, Exception {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        
        // TODO: Check userId

        Optional<Form> form = formRepository.findFormByUserIdAndFormType(userId, formType);
        if (!form.isPresent()) {
            throw new Exception("There is no file that you can download. Make sure to upload a file before downloading.");
        }

        /* 
            Fetches the key based on the user ID assuming the fact that there are only one unique
            files for each file type for each user. The file is read and returned as bytes.
        */
        final String key = form.get().getKey();
        ResponseBytes<GetObjectResponse> s3Object = s3.getObject(
            GetObjectRequest.builder().bucket(bucketName).key(key).build(),
            ResponseTransformer.toBytes());
        final byte[] formByteArray = s3Object.asByteArray();

        return formByteArray;
    }

    public boolean deleteFile(UUID userId, FormEnum formType) {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;

        // TODO: Check userId
        Optional<Form> form = formRepository.findFormByUserIdAndFormType(userId, formType);
        if (!form.isPresent()) {
            return false; // TODO: Throw custom error
        }
        
        /* 
            Fetches the key based on the user ID assuming the fact that there are only one unique
            files for each file type for each user. The file is deleted, the deletion status is returned.
            Removal is done in 2 steps. Firstly, the actual file instance on Amazon S3 is deleted. Then,
            the row of the form where the key value is stored is deleted.
        */
        final String key = form.get().getKey();
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();
        s3.deleteObject(deleteObjectRequest);
        
        // Remove key from the database
        formRepository.deleteByUserId(userId);
        return true;
    }
}
