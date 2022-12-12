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
@RequestMapping("api/v1")
public class FileController {
    private final FileService fileService;

    /**
     * 
     * @param file Multipart file sent from the backend containing a form. 
     * @param studentId UUID of the user sending the request.
     * @return
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "student/preApproval/{studentUuid}", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadPreApproval(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("fileType") FileEnum fileType,
                                                    @PathVariable(value="studentUuid") String studentUuid) {
        try {
            // TODO: Instead of studentUuid pass bilkent student ID.
            fileService.uploadFile(file, studentUuid, fileType);
            return Response.create("Successfully uploaded the file", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "student/preApproval/{studentUuid}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deletePreApproval(@PathVariable(value = "studentId") String studentUuid) {
        try {
            // TODO: fetch file key based on user uuid
            // fileService.deleteFile()
            return Response.create("Successfully deleted the file", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "student/preApproval/{studentUuid}", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadPreApproval(@PathVariable(value = "studentId") String studentUuid) {
        try {
            // TODO: fetch file key based on user uuid. send back the response of file service to frontend
            fileService.downloadFile(studentUuid);
            return Response.create("Successfully downloaded the file", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }
}
