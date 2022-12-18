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
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.model.InstructorCourse;
import com.beam.beamBackend.request.InstructorCourseAdd;
import com.beam.beamBackend.request.InstructorCourseApproval;
import com.beam.beamBackend.response.RInstructorCourseAdd;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.InstructorCourseService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/instructor")
public class InstructorCourseController {
    private final InstructorCourseService instructorService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "course")
    public ResponseEntity<Object> addCourseToInstructor(@Valid @RequestBody InstructorCourseAdd courseInfo) {
        try {
            RInstructorCourseAdd result = instructorService.addCourseToInstructor(courseInfo);
            return Response.create("account is created", HttpStatus.OK, result);
        } catch (Exception e) {
            return Response.create("account creation is failed", HttpStatus.CONFLICT); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("{instructorId}/course")
    public ResponseEntity<Object> getInstructorCourses(@Valid @PathVariable("instructorId") UUID instructorId) {
        try {
            List<BilkentCourse> courseList = instructorService.getInstructorCourses(instructorId);
            return Response.create("ok", HttpStatus.OK, courseList);
        } catch (Exception e) {
            return Response.create("accounts cannot be retrieved", HttpStatus.OK); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{instructorId}/course/requested")
    public ResponseEntity<Object> getInstructorRequestedCourses(@Valid @PathVariable("instructorId") UUID instructorId) {
        try {
            List<CourseRequest> courseList = instructorService.getInstructorRequestedCourses(instructorId);
            return Response.create("ok", HttpStatus.OK, courseList);
        } catch (Exception e) {
            return Response.create("accounts cannot be retrieved", HttpStatus.OK); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/course/determineStatus")
    public ResponseEntity<Object> decideCourseRequestStatus(@Valid @RequestBody InstructorCourseApproval requestResult) {
        try {
            CourseRequest result = instructorService.determineRequestedCourseStatus(requestResult);
            return Response.create("ok", HttpStatus.OK, result);
        } catch (Exception e) {
            return Response.create("accounts cannot be retrieved", HttpStatus.OK); // might change later
        }        
    }
}
