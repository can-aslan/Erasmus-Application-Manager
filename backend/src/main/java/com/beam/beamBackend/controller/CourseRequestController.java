package com.beam.beamBackend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.ICourseRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
// @Secured({"OUTGOING"}) // Temporary user role for outgoing student
@RequestMapping("api/v1/course/student")
@RequiredArgsConstructor
public class CourseRequestController {
    private final ICourseRequestService courseRequestService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(path = "/request", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> requestCourse(@Valid @RequestBody CourseRequest courseRequest)
    {
        try {
            boolean responseResult = courseRequestService.requestCourse(courseRequest);

            return 
            responseResult ?
                Response.create("course requested", HttpStatus.OK)
                :
                Response.create("course request failed", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("course request failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/fetch")
    public ResponseEntity<Object> getAllCourseRequests() {
        try {
            List<CourseRequest> responseResult = courseRequestService.getAllCourseRequests();
            
            return 
            responseResult != null ?
                Response.create("course requests fetch successful", HttpStatus.OK, responseResult)
                :
                Response.create("course requests fetch unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("could not retrieve all course requests", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/fetch/{studentId}")
    public ResponseEntity<Object> getAllCourseRequestsOfStudent(@PathVariable("studentId") Long studentId) {
        try {
            List<CourseRequest> responseResult = courseRequestService.getAllCourseRequestsOfStudent(studentId);
            
            return 
            responseResult != null ?
                Response.create("course requests fetch successful", HttpStatus.OK, responseResult)
                :
                Response.create("course requests fetch unsuccessful", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.create("could not retrieve course requests of student", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
