package com.beam.beamBackend.service.form;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.beam.beamBackend.enums.FormEnum;
import com.beam.beamBackend.model.Signature;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.ISignatureRepository;
import com.beam.beamBackend.service.form.decorator.Clear;
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

@Service
@RequiredArgsConstructor
public class SignatureService implements ISignatureService {
    private final IAccountRepository accountRepository;
    private final ISignatureRepository signatureRepository;
    private final String DEFAULT_BUCKET_NAME = "beam-form-bucket";
    private S3Client s3 = S3ClientSingleton.getInstance();
    final long ONE_KB = 1024;
    final long ONE_MB = ONE_KB * ONE_KB; 
    final long FILE_SIZE_LIMIT = ONE_MB;
    final String FILE_PREFIX = "SIGNATURE";
    
    @Override
    public boolean uploadSignature(UUID userId, MultipartFile file) throws FileSizeLimitExceededException, Exception {
        String bucketName = DEFAULT_BUCKET_NAME;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());
        if (file.getSize() > FILE_SIZE_LIMIT) {
            String errMsg = "Exceeded the file size limit of " + FILE_SIZE_LIMIT;
            throw new FileSizeLimitExceededException(errMsg, file.getSize(), FILE_SIZE_LIMIT);
        }

        Optional<User> user = accountRepository.findById(userId);
        if (!user.isPresent()) {
            throw new Exception("User is not present"); // TODO: Throw usernotfoundexception
        }

        Optional<Signature> signature = signatureRepository.findSignatureByUserId(userId);
        if (signature.isPresent()) {
            signatureRepository.delete(signature.get());
            deleteSignature(userId);
        }

        User u = user.get();
        long bilkentId = u.getBilkentId();
        String bilkentIdStr = Long.toString(bilkentId);
        MultipartFileWrapper fileWrapper = new MultipartFileWrapper(file);
        UniquelyNameable nameable = new Clear(fileWrapper);
        nameable = new Prefix(nameable, bilkentIdStr + "_");
        nameable = new Prefix(nameable, FormEnum.SIGNATURE + "_");
        nameable = new Postfix(nameable, timestamp);
        nameable = new Postfix(nameable, ".png");

        try {
            final String key = nameable.getUniqueName();
            PutObjectRequest objectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(MediaType.IMAGE_PNG_VALUE)
                    .build();
            s3.putObject(objectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            Signature signatureObj = new Signature(UUID.randomUUID(), userId, key);
            signatureRepository.save(signatureObj);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    @Override
    public byte[] downloadSignature(UUID userId) throws IOException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        
        Optional<Signature> signature = signatureRepository.findSignatureByUserId(userId);
        System.out.println("here: ");

        if (!signature.isPresent()) {
            return null;
        }

        final String key = signature.get().getKey();
        System.out.println("Key: " + key);
        ResponseBytes<GetObjectResponse> s3Object = s3.getObject(
            GetObjectRequest.builder().bucket(bucketName).key(key).build(),
            ResponseTransformer.toBytes());
        final byte[] bytes = s3Object.asByteArray();
        return bytes;
    }

    @Override
    public ByteArrayInputStream getSignatureFile(UUID userId) throws IOException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        
        Optional<Signature> signature = signatureRepository.findById(userId);

        if (!signature.isPresent()) {
            return null;
        }

        final String key = signature.get().getKey();
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        byte[] fileInputStream = s3.getObject(getObjectRequest).readAllBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream(fileInputStream);
        return bais;
    }

    @Override
    public void deleteSignature(UUID userId) {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        
        Optional<Signature> signature = signatureRepository.findById(userId);

        if (!signature.isPresent()) {
            return; // TODO: Throws signature not found exception
        }

        final String key = signature.get().getKey(); // TODO: Throw exception if key is null

        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        s3.deleteObject(deleteObjectRequest); // TODO:Handle AWS exception
        signatureRepository.deleteById(userId); // TODO: Not sure if this works
    }

    @Override
    public Optional<Signature> getSignatureByBilkentId(Long bilkentId) {
        User user = accountRepository.findUserByBilkentId(bilkentId);
        UUID id = user.getId();
        return signatureRepository.findSignatureByUserId(id);
    }
}
