package com.beam.beamBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.StudentPlacementService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/placement")
public class StudentPlacementController {

    private final StudentPlacementService studentPlacementService;
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(path = "place")
    public ResponseEntity<Object> placeStudents() throws Exception{
        System.out.println("Am I Here-----------------------------------------------------");
        studentPlacementService.placeStudents();
        return Response.create("Students are placed!", HttpStatus.OK);
    }

}
