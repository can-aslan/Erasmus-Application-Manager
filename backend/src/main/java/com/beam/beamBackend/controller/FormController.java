package com.beam.beamBackend.controller;

import java.io.IOException;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
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
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.form.FormService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/fileService")
public class FormController {
    private final FormService formService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentUuid}", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadForm(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("fileType") FormEnum fileType,
                                                    @PathVariable(value="studentUuid") UUID studentUuid) {
        try {
            boolean upload = formService.uploadForm(file, studentUuid, fileType);
            if (upload == false) {
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
    @RequestMapping(path = "form/{studentUuid}/{formType}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteForm(@PathVariable(value = "studentUuid") UUID studentUuid,
                                                @PathVariable(value = "formType") FormEnum formType) {
        try {
            formService.deleteFile(studentUuid, formType);
            return Response.create("Successfully deleted the file", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File deletion failed", HttpStatus.CONFLICT); // might change later
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentUuid}/{formType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> downloadForm(@PathVariable(value = "studentUuid") UUID studentUuid,
                                                @PathVariable(value = "formType") FormEnum formType) {
        try {
            byte[] file = formService.downloadForm(studentUuid, formType);
            return Response.create("Successfully downloaded the file", HttpStatus.OK, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File download failed", HttpStatus.CONFLICT); // might change later
        }
    }

    // TODO:
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/generate/submit/student/{studentUuid}/{formType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> generateAndSubmitPreApproval(@PathVariable(value = "studentUuid") UUID studentUuid,
                                                                @PathVariable(value = "formType") FormEnum formType) {
        try {
            // TODO: Send the already generated file rather than calling S3
            byte[] file = formService.downloadForm(studentUuid, formType);
            return Response.create("Successfully downloaded the file", HttpStatus.OK, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File submit failed", HttpStatus.CONFLICT); // might change later
        }
    }
    
    // TODO:
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/generate/download/{studentUuid}/{formType}", method = RequestMethod.POST, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> generateAndDownloadPreApproval(@PathVariable(value = "studentUuid") UUID studentUuid,
                                                                    @PathVariable(value = "formType") FormEnum formType) {

        try {
            // TODO: Send the already generated file rather than calling S3
            byte[] file = formService.downloadForm(studentUuid, formType);
            return Response.create("Successfully downloaded the file", HttpStatus.OK, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }
}
