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
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.form.ISignatureService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/signature")
public class SignatureController {
    private ISignatureService signatureService;
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "user/{userId}", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadSignature(@RequestParam("signature") MultipartFile signature, 
        @PathVariable("userId") UUID userId) {
        try {
            signatureService.uploadSignature(userId, signature);
            return Response.create("Signature submission successful", HttpStatus.OK);  
        } catch (FileSizeLimitExceededException e) {
            return Response.create("Exceeded the file size limit.", HttpStatus.BAD_REQUEST);  
        } catch (Exception e) {
            return Response.create(e.getMessage(), HttpStatus.BAD_REQUEST);  
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "user/{userId}", method = RequestMethod.GET, produces = { MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<Object> downloadSignature(@PathVariable("userId") UUID userId) {
        try {
            byte[] signatureByteStream = signatureService.downloadSignature(userId);
            ByteArrayResource resource = new ByteArrayResource(signatureByteStream);

            return ResponseEntity
                    .ok()
                    .body(resource);
        } catch (IOException e) {
            return Response.create(e.getMessage(), HttpStatus.BAD_REQUEST);  
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @RequestMapping(path = "user/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSignature(@PathVariable("userId") UUID userId) {
        try {
            signatureService.deleteSignature(userId);
            return Response.create("Signature submission successful", HttpStatus.OK);  
        } catch (Exception e) {
            return Response.create(e.getMessage(), HttpStatus.BAD_REQUEST);  
        }
    }
}
