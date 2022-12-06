package com.beam.beamBackend.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.File;
import com.beam.beamBackend.service.IStudentCourseRequestService;

import lombok.RequiredArgsConstructor;

@RestController
@Secured({"OUTGOING"}) // Temporary user role for outgoing student
@RequestMapping("api/v1/course/request") // <- temporary
@RequiredArgsConstructor
public class StudentCourseRequestController {
    private final IStudentCourseRequestService studentCourseRequestService;

    @PostMapping("{studentId}/{code}/{name}/{bilkentCourse}/{webpage}") // <- temporary
    public ResponseEntity<Boolean> requestCourse(
        @PathVariable("studentId") String studentId,
        @PathVariable("code") String code,
        @PathVariable("name") String name,
        @PathVariable("bilkentCourse") String bilkentCourse,
        @PathVariable("webpage") String webpage
    )
    {
        return ResponseEntity.ok(studentCourseRequestService.requestCourse(UUID.fromString(studentId), code, name, bilkentCourse, webpage));
    }

    @PostMapping("{studentId}/{code}/{name}/{bilkentCourse}/{webpage}/{additionalInfo}") // <- temporary
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

    @GetMapping
    public ResponseEntity<ArrayList<Course>> getPreviouslyRequestedCourses() {
        return ResponseEntity.ok(studentCourseRequestService.getPreviouslyRequestedCourses());
    }
}
