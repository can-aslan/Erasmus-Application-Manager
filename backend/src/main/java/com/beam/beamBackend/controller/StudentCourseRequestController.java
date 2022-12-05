package com.beam.beamBackend.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.File;
import com.beam.beamBackend.service.IStudentCourseRequestService;

@RestController
@Secured("OUTGOING") // Temporary user role for outgoing student
@RequestMapping("courses/request") // <- temporary
// @RequiredArgsConstructor
public class StudentCourseRequestController {
    private final IStudentCourseRequestService studentCourseRequestService;

    @Autowired
    public StudentCourseRequestController(IStudentCourseRequestService studentCourseRequestService) {
        this.studentCourseRequestService = studentCourseRequestService;
    }

    @PutMapping("{studentId}/{code}/{name}/{bilkentCourse}/{webpage}") // <- temporary
    public ResponseEntity<Boolean> requestCourse(
        @PathVariable("studentId") UUID studentId,
        @PathVariable("code") String code,
        @PathVariable("name") String name,
        @PathVariable("bilkentCourse") Course bilkentCourse,
        @PathVariable("webpage") String webpage
    )
    {
        return ResponseEntity.ok(studentCourseRequestService.requestCourse(studentId, code, name, bilkentCourse, webpage));
    }

    @PutMapping("{studentId}/{code}/{name}/{bilkentCourse}/{webpage}/{additionalInfo}") // <- temporary
    public ResponseEntity<Boolean> requestCourse(
        @PathVariable("studentId") UUID studentId,
        @PathVariable("code") String code,
        @PathVariable("name") String name,
        @PathVariable("bilkentCourse") Course bilkentCourse,
        @PathVariable("webpage") String webpage,
        @PathVariable("additionalInfo") File additionalInfo
    )
    {
        return ResponseEntity.ok(studentCourseRequestService.requestCourse(studentId, code, name, bilkentCourse, webpage, additionalInfo));
    }

    @GetMapping("getPreviouslyRequestedCourses") // <- temporary
    public ResponseEntity<ArrayList<Course>> getPreviouslyRequestedCourses() {
        return ResponseEntity.ok(studentCourseRequestService.getPreviouslyRequestedCourses());
    }
}
