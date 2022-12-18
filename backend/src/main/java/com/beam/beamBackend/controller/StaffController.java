package com.beam.beamBackend.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.request.StaffRequest;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IStaffService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/staff")
public class StaffController {
    private final IStaffService staffService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "")
    public ResponseEntity<Object> addStaff(@Valid @RequestBody StaffRequest staff) {
        try {
            System.out.println(staff);
            UUID id = staffService.addStaff(staff);
            return Response.create("staff is added", HttpStatus.OK, id);
        } catch (Exception e) {
            return Response.create("staff is not created", 499); // might change later
        }        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping
    public ResponseEntity<Object> getStaff() {
        try {
            List<Staff> staff = staffService.getAllStaff();
            return Response.create("staff is retrieved", HttpStatus.OK, staff);
        } catch (Exception e) {
            return Response.create("staff is not retrieved", 499); // might change later
        }        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/coordinator")
    public ResponseEntity<Object> getCoordinatorsByDepartment(@Valid @RequestParam("department") Department department) {
        try {
            List<Staff> staff = staffService.getCoordinatorsByDepartment(department);
            return Response.create("staff is retrieved", HttpStatus.OK, staff);
        } catch (Exception e) {
            return Response.create("staff is not retrieved", 499); // might change later
        }        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/coordinator/{userId}")
    public ResponseEntity<Object> getCoordinatorById(@Valid @PathVariable("userId") UUID userId) {
        try {
            Staff staff = staffService.getStaffByUserId(userId);
            return Response.create("staff is retrieved", HttpStatus.OK, staff);
        } catch (Exception e) {
            return Response.create("staff is not retrieved", 499); // might change later
        }        
    }
}
