package com.beam.beamBackend.service.form;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.UUID;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.beam.beamBackend.enums.CourseWishlistStatus;
import com.beam.beamBackend.enums.FormEnum;
import com.beam.beamBackend.enums.PreApprovalStatus;
import com.beam.beamBackend.model.Form;
import com.beam.beamBackend.model.PreApprovalForm;
import com.beam.beamBackend.model.Signature;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IFormRepository;
import com.beam.beamBackend.repository.IPreApprovalRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IWishlistRepository;
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
import com.beam.beamBackend.model.Wishlist;

import java.util.Base64;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormService implements IFormService {
    private final IFormRepository formRepository;
    private final IAccountRepository accountRepository;
    private final IWishlistRepository wishlistRepository;
    private final IPreApprovalRepository preApprovalRepository;
    private final IStudentRepository studentRepository;
    private final IFileGeneratorService fileGenerator;
    private final ISignatureService signatureService;
    private final String DEFAULT_BUCKET_NAME = "beam-form-bucket";
    final long ONE_KB = 1024;
    final long ONE_MB = ONE_KB * ONE_KB; 
    final long FILE_SIZE_LIMIT = ONE_MB;
    
    @Override
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

    @Override
    public boolean uploadForm(File file, UUID userId, FormEnum formType) throws FileSizeLimitExceededException {
        S3Client s3 = S3ClientSingleton.getInstance();
        String bucketName = DEFAULT_BUCKET_NAME;
        String timestamp = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());


        Optional<Form> form = formRepository.findFormByUserIdAndFormType(userId, formType);
        if (form.isPresent()) {
            deleteFile(userId, formType);
        }

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
        Form formObj = new Form(id, userId, formType, key);
        formRepository.save(formObj);

        return true;
    }

    @Override
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

    @Override
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

    @Override
    public byte[] generateAndDownloadPreApproval(UUID studentId) throws Exception {
        try {
            PreApprovalForm preApprovalForm = createPreAppFromWishlist(studentId, false);
            File approvalForm = fileGenerator.generatePreApprovalForm(preApprovalForm, null);
            FileInputStream fis = new FileInputStream(approvalForm);
            byte[] form = fis.readAllBytes();
            fis.close();

            byte[] encoded = Base64.getEncoder().encode(form);
            return encoded;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void generateAndSubmitPreApproval(FormEnum formType, UUID studentId) throws Exception {
        try {
            PreApprovalForm form = createPreAppFromWishlist(studentId, true);
            File approvalForm = fileGenerator.generatePreApprovalForm(form, null);
            uploadForm(approvalForm, studentId, formType);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }

    @Override
    public void signPreApproval(Long studentBilkentId, Long coordinatorBilkentId) throws Exception {
        PreApprovalForm currentForm = getPreApprovalForm(studentBilkentId);
        User coordinator = accountRepository.findUserByBilkentId(coordinatorBilkentId);
        User student = accountRepository.findUserByBilkentId(studentBilkentId);
        Optional<Signature> signature = signatureService.getSignatureByBilkentId(coordinatorBilkentId);

        if (!signature.isPresent()) {
            throw new Exception("Signature not find for coordinator");
        }

        Signature signatureObj = signature.get();
        final String key = signatureObj.getKey();

        // Write signature to a temp file
        byte[] signatureByteArray = signatureService.downloadSignature(coordinator.getId());

        // File tempFile = File.createTempFile("sign", ".png", null);
        // FileOutputStream fos = new FileOutputStream(tempFile);
        // fos.write(signatureByteArray);

        // Convert signature to buffered image and pass it to generate preapproval form
        // BufferedImage signatureImg = ImageIO.read(tempFile);

        File approvalForm = fileGenerator.generatePreApprovalForm(currentForm, signatureByteArray);

        // tempFile.delete();

        uploadForm(approvalForm, student.getId(), FormEnum.PRE_APPROVAL);
    }

    /**
     * Creates a PreApproval instance for the student if they do not have one
     * Modifies the PreApproval instance information if they have one
     * Creation and modification are done according to the current wishlist of the student
     * If the student do not have a wishlist, a preApproval cannot be created
     * @param userUuid user id of the student whose pre approval is supposed to be created
     * @throws Exception
     */
    @Override
    public PreApprovalForm createPreAppFromWishlist(UUID studentId, boolean isSavedToDatabase) throws Exception {
        try{
            Optional<Student> student = studentRepository.findByUserId(studentId);
            if (!student.isPresent()){
                throw new Exception("Student with given user id does not exist!");
            }

            Optional<Wishlist> wishlist = wishlistRepository.findByStudentId(student.get().getUser().getBilkentId());

            if (!wishlist.isPresent()){
                throw new Exception("Student does not have a wishlist!");
            }

            if (wishlist.get().getStatus() != CourseWishlistStatus.APPROVED ){
                throw new Exception("Student's wishlist has not been approved!");
            }

            boolean preApprovalExist = preApprovalRepository.existsByWishlistStudentId(student.get().getUser().getBilkentId());
            if (preApprovalExist) {
                throw new Exception("pre approval already exists for this student");
            }
            
            // Create instant date object here
            String date = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new java.util.Date());
            Wishlist wishlistObj = wishlist.get();
            Student studentObj = student.get();
            PreApprovalForm newForm = new PreApprovalForm(UUID.randomUUID(), studentObj, wishlistObj, date, PreApprovalStatus.PENDING);

            if (isSavedToDatabase) {
                preApprovalRepository.save(newForm);
            }

            return newForm;
        
        } catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public PreApprovalForm getPreApprovalForm(Long bilkentId) throws Exception {
        Optional<Student> student = studentRepository.findByUserBilkentId(bilkentId);

        if (!student.isPresent()){
            throw new Exception("Student is not found!");
        } else {
            try{
                Optional<PreApprovalForm> preApprovalForm = preApprovalRepository.findByStudentUserBilkentId(bilkentId);

                if (!preApprovalForm.isPresent()){
                    throw new Exception("PreApproval form for the student is not present!");
                }

                return preApprovalForm.get();
            } catch(Exception e){
                e.printStackTrace();  
                throw e;
            }
        }
    }

    @Override
    public PreApprovalStatus getPreApprovalStatus(Long studentId) {
        Optional<PreApprovalForm> preApprovalForm = preApprovalRepository.findByStudentUserBilkentId(studentId);
        if (!preApprovalForm.isPresent()) {
            return PreApprovalStatus.WAITING;
        }

        return preApprovalForm.get().getPreApprovalStatus();
    }
}
