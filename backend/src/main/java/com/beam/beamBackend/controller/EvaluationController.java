package com.beam.beamBackend.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.UniEvaluationForm;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.request.ChangePassword;
import com.beam.beamBackend.response.RLoginUser;
import com.beam.beamBackend.response.RRefreshToken;
import com.beam.beamBackend.response.RUserList;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.response.ResponseId;
import com.beam.beamBackend.service.AccountService;
import com.beam.beamBackend.service.EvaluationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/eval")
public class EvaluationController {
    private final EvaluationService evalService;

    // @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "evaluateUni")
    public ResponseEntity<Object> evaluateUni(@Valid @RequestBody UniEvaluationForm uniEval) {
        try {
            UUID id = evalService.evaluateUni(uniEval);
            return Response.create("university evaluation is saved", HttpStatus.OK, id);
        } catch (Exception e) {
            return Response.create("evaluation failed", 499); // might change later
        }        
    }

    // @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/uniEval/{uniId}")
    public ResponseEntity<Object> getUniEvals(@Valid @PathVariable("uniId") UUID uniId) {
        try {
            List<UniEvaluationForm> uniEvals = evalService.getUniEval(uniId);
            return Response.create("ok", HttpStatus.OK, uniEvals);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST); // might change later
        }        
    }
}
