package com.beam.beamBackend.controller;

import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IStudentPlacementService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/placement")
public class StudentPlacementController {
    private final IStudentPlacementService studentPlacementService;
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(path = "place/{department}")
    public ResponseEntity<Object> placeStudents(@PathVariable("department") String department) {
        try {
            ArrayList<Student> registeredStundets = studentPlacementService.placeStudents(department);
            return Response.create("Students are placed!", HttpStatus.OK, registeredStundets);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("Students could not be placed!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
