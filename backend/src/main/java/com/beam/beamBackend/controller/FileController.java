package com.beam.beamBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.beam.beamBackend.aws.FileService;
import com.beam.beamBackend.response.Response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1")
public class FileController {
    private final FileService fileService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(path="/student/uploadPreApproval/{studentId}")
    public ResponseEntity<Object> uploadPreApproval(@RequestParam("file") MultipartFile file,
                                                    @PathVariable(value="studentId") String studentId) {
        System.out.println(studentId);
        try {
            fileService.uploadFile(file, studentId);
            return Response.create("Successfully uploaded the file", HttpStatus.OK);
        }
        catch (Exception e) {
            return Response.create("File upload failed", HttpStatus.CONFLICT); // might change later
        }
    }
}
