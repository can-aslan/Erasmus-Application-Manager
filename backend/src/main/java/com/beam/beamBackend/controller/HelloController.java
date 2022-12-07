package com.beam.beamBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.response.Response;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("hello")
public class HelloController {
    
    @GetMapping
    public ResponseEntity<Object> testAuth() {
        try {
            String test = "Hi, this is BEAM backend team!";
            return Response.create("ok", HttpStatus.OK, test);
        } catch (Exception e) {
            return Response.create("hello failed", 312); // might change later
        }        
    }
}
