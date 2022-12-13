package com.beam.beamBackend.controller;

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

import com.beam.beamBackend.enums.FileEnum;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.file.FileService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/fileService")
public class FileController {
    private final FileService fileService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentUuid}", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadForm(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("fileType") FileEnum fileType,
                                                    @PathVariable(value="studentUuid") String studentUuid) {
        try {
            fileService.uploadFile(file, studentUuid, fileType);
            return Response.create("Successfully uploaded the file", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentUuid}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteForm(@PathVariable(value = "studentId") String studentUuid) {
        try {
            fileService.deleteFile(studentUuid);
            return Response.create("Successfully deleted the file", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/{studentUuid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> downloadForm(@PathVariable(value = "studentId") String studentUuid) {
        try {
            byte[] file = fileService.downloadFile(studentUuid);
            return Response.create("Successfully downloaded the file", HttpStatus.OK, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/generate/submit/{studentUuid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> generateAndSubmitPreApproval(@PathVariable(value = "studentId") String studentUuid) {
        try {
            byte[] file = fileService.downloadFile(studentUuid);
            return Response.create("Successfully downloaded the file", HttpStatus.OK, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "form/generate/download/{studentUuid}", method = RequestMethod.POST, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Object> generateAndDownloadPreApproval(@PathVariable(value = "studentId") String studentUuid) {
        try {
            byte[] file = fileService.downloadFile(studentUuid);
            return Response.create("Successfully downloaded the file", HttpStatus.OK, file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }
}
