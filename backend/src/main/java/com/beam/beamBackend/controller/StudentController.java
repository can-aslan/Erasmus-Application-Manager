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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.HostCourse;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.request.StudentRequest;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.StudentService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/student")
public class StudentController {

    private final StudentService studentService;
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "")
    public ResponseEntity<Object> addStudent(@Valid @RequestBody StudentRequest student) {
        try {
            System.out.println(student);
            UUID id = studentService.addStudent(student);
            return Response.create("student is added", HttpStatus.OK, id);
        } catch (Exception e) {
            return Response.create("student is not created", 499); // might change later
        }        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping
    public ResponseEntity<Object> getStudents() {
        try {
            List<Student> students = studentService.getAll();
            return Response.create("student is added", HttpStatus.OK, students);
        } catch (Exception e) {
            return Response.create("student is not created", 499); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{bilkentId}")
    public ResponseEntity<Object> getStudentByBilkentId(@Valid @PathVariable("bilkentId") Long bilkentId) {
        try {
            Student student = studentService.getStudentByBilkentId(bilkentId);
            return Response.create("student is retrieved", HttpStatus.OK, student);
        } catch (Exception e) {
            return Response.create("student is not created", 499); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{bilkentId}/uniId")
    public ResponseEntity<Object> getStudentUni(@Valid @PathVariable("bilkentId") Long bilkentId) {
        try {
            UUID uniId = studentService.getUniIdOfStudent(bilkentId);
            return Response.create("uni id of student is retrieved", HttpStatus.OK, uniId);
        } catch (Exception e) {
            return Response.create("student is not created", 499); // might change later
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/{bilkentId}/uni/course")
    public ResponseEntity<Object> getStudentUniCourses(@Valid @PathVariable("bilkentId") Long bilkentId) {
        try {
            List<HostCourse> courses = studentService.getHostCoursesOfStudentHostUni(bilkentId);
            return Response.create("uni id of student is retrieved", HttpStatus.OK, courses);
        } catch (Exception e) {
            return Response.create(e.getLocalizedMessage() + " : " + e.getMessage(), 499); // might change later
        }        
    }

    // @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    // @GetMapping("/{id}")
    // public ResponseEntity<Object> getStudentById(@Valid @PathVariable("id") UUID id) {
    //     try {
    //         Student student = studentService.getStudentById(id);
    //         return Response.create("student is added", HttpStatus.OK, student);
    //     } catch (Exception e) {
    //         return Response.create("student is not created", 499); // might change later
    //     }        
    // }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "")
    public ResponseEntity<Object> updateStudent(@Valid @RequestBody StudentRequest student) {
        try {
            Student updated = studentService.updateStudent(student);
            return Response.create("student is added", HttpStatus.OK, updated);
        } catch (Exception e) {
            return Response.create("student is not created", 499); // might change later
        }        
    }
}
