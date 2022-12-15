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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.UniEvaluationForm;
import com.beam.beamBackend.response.RCourseEval;
import com.beam.beamBackend.response.RUniEval;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.EvaluationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/eval")
public class EvaluationController {
    private final EvaluationService evalService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "university")
    public ResponseEntity<Object> evaluateUni( @RequestBody UniEvaluationForm uniEval) {
        try {
            System.out.println(uniEval);
            System.out.println(uniEval.getRate());
            UUID id = evalService.evaluateUni(uniEval);
            return Response.create("university evaluation is saved", HttpStatus.OK, id);
        } catch (Exception e) {
            return Response.create("evaluation failed", 499); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/university/{uniId}")
    public ResponseEntity<Object> getUniEvals(@Valid @PathVariable("uniId") UUID uniId) {
        try {
            RUniEval uniEvals = evalService.getUniEval(uniId);
            return Response.create("ok", HttpStatus.OK, uniEvals);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "course")
    public ResponseEntity<Object> evaluateCourse(@Valid @RequestBody CourseEvaluationForm courseEval) {
        try {
            System.out.println(courseEval);
            UUID id = evalService.evaluateCourse(courseEval);
            return Response.create("university evaluation is saved", HttpStatus.OK, id);
        } catch (Exception e) {
            return Response.create("evaluation failed", 499); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/course/{courseId}")
    public ResponseEntity<Object> getCourseEval(@Valid @PathVariable("courseId") UUID courseId) {
        try {
            RCourseEval courseEvals = evalService.getCourseEval(courseId);
            return Response.create("ok", HttpStatus.OK, courseEvals);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("student/{studentId}/university")
    public ResponseEntity<Object> getSavedUniEval(@Valid @PathVariable("studentId") long authorId, @Valid @RequestParam("eval_status") EvalStatus evalStatus) {
        try {
            UniEvaluationForm uniEval = evalService.getStudentUniEval(authorId, evalStatus);
            return Response.create("ok", HttpStatus.OK, uniEval);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("student/{studentId}/course/{courseId}")
    public ResponseEntity<Object> getSavedCourseEval(@Valid @PathVariable("studentId") long authorId, @Valid @RequestParam("eval_status") EvalStatus evalStatus) {
        try {
            CourseEvaluationForm courseEval = evalService.getStudentCourseEval(authorId, evalStatus);
            return Response.create("ok", HttpStatus.OK, courseEval);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST); // might change later
        }        
    }
}
