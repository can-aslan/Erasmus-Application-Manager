package com.beam.beamBackend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IStudentCourseRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
// @Secured({"OUTGOING"}) // Temporary user role for outgoing student
@RequestMapping("api/v1/course/student")
@RequiredArgsConstructor
public class StudentCourseRequestController {
    private final IStudentCourseRequestService studentCourseRequestService;

    @PostMapping(path = "/request", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> requestCourse(@Valid @RequestBody CourseRequest courseRequest)
    {
        boolean responseResult = studentCourseRequestService.requestCourse(courseRequest);

        return 
            responseResult ?
                Response.create("course requested", HttpStatus.OK)
                :
                Response.create("course request failed", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/fetch")
    public ResponseEntity<Object> getAllCourseRequests() {
        List<CourseRequest> responseResult = studentCourseRequestService.getAllCourseRequests();
        return 
            responseResult != null ?
                Response.create("course requests fetch successful", HttpStatus.OK, responseResult)
                :
                Response.create("course requests fetch unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/fetch/{studentId}")
    public ResponseEntity<Object> getAllCourseRequestsOfStudent(@PathVariable("studentId") Long studentId) {
        List<CourseRequest> responseResult = studentCourseRequestService.getAllCourseRequestsOfStudent(studentId);
        return 
            responseResult != null ?
                Response.create("course requests fetch successful", HttpStatus.OK, responseResult)
                :
                Response.create("course requests fetch unsuccessful", HttpStatus.BAD_REQUEST);
    }
    
    /* DEPRECATED, NOT USED ANYMORE
    @PostMapping("{studentId}/{code}/{name}/{bilkentCourse}/{webpage}/{additionalInfo}")
    public ResponseEntity<Boolean> requestCourse(
        @PathVariable("studentId") String studentId,
        @PathVariable("code") String code,
        @PathVariable("name") String name,
        @PathVariable("bilkentCourse") String bilkentCourse,
        @PathVariable("webpage") String webpage,
        @PathVariable("additionalInfo") File additionalInfo
    )
    {
        return ResponseEntity.ok(studentCourseRequestService.requestCourse(UUID.fromString(studentId), code, name, bilkentCourse, webpage, additionalInfo));
    }
    */
}
