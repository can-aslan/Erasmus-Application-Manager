package com.beam.beamBackend.service.form;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;
import com.beam.beamBackend.enums.FormEnum;
import com.beam.beamBackend.enums.PreApprovalStatus;
import com.beam.beamBackend.model.PreApprovalForm;

public interface IFormService {
    boolean uploadForm(MultipartFile file, UUID userId, FormEnum formType) throws IOException, FileSizeLimitExceededException, UsernameNotFoundException, Exception;
    boolean uploadForm(File file, UUID userId, FormEnum formType) throws FileSizeLimitExceededException;
    byte[] downloadForm(UUID userId, FormEnum formType) throws IOException, Exception;
    boolean deleteFile(UUID userId, FormEnum formType);
    ByteArrayResource generateAndDownloadPreApproval(UUID studentId) throws Exception;
    void generateAndSubmitPreApproval(FormEnum formType, UUID studentId) throws Exception;
    void signPreApproval(Long studentBilkentId, Long coordinatorBilkentId) throws Exception;
    PreApprovalForm createPreAppFromWishlist(UUID studentId) throws Exception;
    PreApprovalForm getPreApprovalForm(Long bilkentId) throws Exception;
    PreApprovalStatus getPreApprovalStatus(Long studentId);
}
