package com.beam.beamBackend.controller;

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

import com.beam.beamBackend.exception.ExceptionLogger;
import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.UniEvaluationForm;
import com.beam.beamBackend.response.RCourseEval;
import com.beam.beamBackend.response.RUniEval;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IEvaluationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/eval")
public class EvaluationController {
    private final IEvaluationService evalService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "university")
    public ResponseEntity<Object> evaluateUni(@Valid @RequestBody UniEvaluationForm uniEval) {
        try {
            System.out.println(uniEval);
            System.out.println(uniEval.getRate());
            UUID id = evalService.evaluateUni(uniEval);
            return Response.create("university evaluation is saved", HttpStatus.OK, id);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/university/{uniId}")
    public ResponseEntity<Object> getUniEvals(@Valid @PathVariable("uniId") UUID uniId) {
        try {
            RUniEval uniEvals = evalService.getUniEval(uniId);
            return Response.create("ok", HttpStatus.OK, uniEvals);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.BAD_REQUEST);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "course")
    public ResponseEntity<Object> evaluateCourse(@Valid @RequestBody CourseEvaluationForm courseEval) {
        try {
            System.out.println(courseEval);
            UUID id = evalService.evaluateCourse(courseEval);
            return Response.create("course evaluation is saved", HttpStatus.OK, id);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<Object> getCourseEval(@Valid @PathVariable("courseId") UUID courseId) {
        try {
            RCourseEval courseEvals = evalService.getCourseEval(courseId);
            return Response.create("ok", HttpStatus.OK, courseEvals);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.BAD_REQUEST);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("student/{studentId}/university")
    public ResponseEntity<Object> getSavedUniEval(@Valid @PathVariable("studentId") long authorId) {
        try {
            UniEvaluationForm uniEval = evalService.getStudentUniEval(authorId);
            return Response.create("ok", HttpStatus.OK, uniEval);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.BAD_REQUEST);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("student/{studentId}/course/{courseId}")
    public ResponseEntity<Object> getSavedCourseEval(@Valid @PathVariable("studentId") long authorId, @Valid @PathVariable("courseId") UUID courseId) {
        try {
            CourseEvaluationForm courseEval = evalService.getStudentCourseEval(authorId, courseId);
            return Response.create("ok", HttpStatus.OK, courseEval);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.BAD_REQUEST);  
        }        
    }
}
