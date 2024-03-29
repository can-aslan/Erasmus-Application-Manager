package com.beam.beamBackend.controller;

import java.io.IOException;
import java.util.UUID;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.beam.beamBackend.enums.FormEnum;
import com.beam.beamBackend.enums.PreApprovalStatus;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.form.IFormService;

import lombok.AllArgsConstructor;

/**
 * 
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/fileService")
public class FormController {
    private final IFormService formService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentId}", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadForm(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("fileType") FormEnum formType,
                                                    @PathVariable(value="studentId") UUID studentId) {
        try {
            boolean upload = formService.uploadForm(file, studentId, formType);
            if (!upload) {
                return Response.create("Something went terribly wrong on AWS servers. We are trying to fix the issue.", HttpStatus.BAD_REQUEST);
            }

            return Response.create("Successfully uploaded the file", HttpStatus.OK, "string");
        } catch (FileSizeLimitExceededException e) {
            return Response.create(e.getMessage(), HttpStatus.CONFLICT); 
        } catch (IOException e) {
            return Response.create("There has been some trouble reading the file you uploaded. Please make sure the file is not corrupted.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return Response.create(e.getMessage(), HttpStatus.CONFLICT); 
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentId}/{formType}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteForm(@PathVariable(value = "studentId") UUID studentId,
                                                @PathVariable(value = "formType") FormEnum formType) {
        try {
            boolean success = formService.deleteFile(studentId, formType);
            if (!success) {
                return Response.create("Bad request", HttpStatus.BAD_REQUEST);
            }
            return Response.create("Successfully deleted the file", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File deletion failed", HttpStatus.CONFLICT);  
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentId}/{formType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> downloadForm(@PathVariable(value = "studentId") UUID studentId,
                                                @PathVariable(value = "formType") FormEnum formType) {
        try {
            byte[] form = formService.downloadForm(studentId, formType);
            ByteArrayResource resource = new ByteArrayResource(form);

            return ResponseEntity
                    .ok()
                    .body(resource);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File download failed", HttpStatus.CONFLICT);  
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/generate/submit/student/{studentId}/{formType}", method = RequestMethod.POST)
    public ResponseEntity<Object> generateAndSubmitPreApproval(@PathVariable(value = "studentId") UUID studentId,
                                                                @PathVariable(value = "formType") FormEnum formType) {
        try {
            formService.generateAndSubmitPreApproval(formType, studentId);
            return Response.create("Successfully submitted the file.", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File submit failed.", HttpStatus.CONFLICT);  
        }
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/generate/download/student/{studentId}/{formType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Object> generateAndDownloadPreApproval(@PathVariable(value = "studentId") UUID studentId,
                                                                    @PathVariable(value = "formType") FormEnum formType) {
        try {
            byte[]  resource = formService.generateAndDownloadPreApproval(studentId);
            return ResponseEntity
                    .ok()
                    .body(new String(resource));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT);  
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/sign/student/{studentBilkentId}/coordinator/{coordinatorBilkentId}/{formType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> signPreApproval(@PathVariable(value = "studentBilkentId") Long studentBilkentId,
                                                    @PathVariable(value = "coordinatorBilkentId") Long coordinatorBilkentId,
                                                    @PathVariable(value = "formType") FormEnum formType) {
        try {
            formService.signPreApproval(studentBilkentId, coordinatorBilkentId);
            return Response.create("Signed the file successfully", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File sign failed", HttpStatus.CONFLICT);  
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/student/{studentId}/preApproval/status/", method = RequestMethod.GET)
    public ResponseEntity<Object> getPreApprovalStatus(@PathVariable(value = "studentId") Long studentId) {
        try {
            PreApprovalStatus status = formService.getPreApprovalStatus(studentId);
            return Response.create("Fetched pre-approval status successfully", HttpStatus.OK, status);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("Pre-approval status fetch failed", HttpStatus.CONFLICT);  
        }
    }
}
