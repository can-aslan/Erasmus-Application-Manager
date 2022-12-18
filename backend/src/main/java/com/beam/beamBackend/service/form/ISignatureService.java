package com.beam.beamBackend.service.form;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.web.multipart.MultipartFile;
import com.beam.beamBackend.model.Signature;

public interface ISignatureService {
    boolean uploadSignature(UUID userId, MultipartFile file) throws FileSizeLimitExceededException;
    byte[] downloadSignature(UUID userId) throws IOException;
    ByteArrayInputStream getSignatureFile(UUID userId) throws IOException;
    void deleteSignature(UUID userId);
    Optional<Signature> getSignatureByBilkentId(Long bilkentId);
}
