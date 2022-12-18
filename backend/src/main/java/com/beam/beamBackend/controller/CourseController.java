package com.beam.beamBackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.beam.beamBackend.service.ICourseService;
import lombok.AllArgsConstructor;
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
import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.HostCourse;
import com.beam.beamBackend.request.ApprovedCourse;
import com.beam.beamBackend.response.Response;
import jakarta.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/course")
public class CourseController {
    private final ICourseService courseService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "bilkent")
    public ResponseEntity<Object> addBilkentCourse(@Valid @RequestBody BilkentCourse bilkentCourse) {
        try {
            System.out.println(bilkentCourse);
            BilkentCourse course = courseService.addBilkentCourse(bilkentCourse);
            return Response.create("course is saved", HttpStatus.OK, course);
        } catch (Exception e) {
            return Response.create("course add failed", 499);
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "host")
    public ResponseEntity<Object> addHostCourse(@Valid @RequestBody HostCourse hostCourse) {
        try {
            System.out.println(hostCourse);
            HostCourse course = courseService.addHostCourse(hostCourse);
            return Response.create("course is saved", HttpStatus.OK, course);
        } catch (Exception e) {
            return Response.create("course add failed", 499);
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("host/{courseId}")
    public ResponseEntity<Object> getHostCourseById(@Valid @PathVariable("courseId") UUID courseId) {
        try {
            HostCourse hostCourse = courseService.getHostCourseById(courseId);
            return Response.create("ok", HttpStatus.OK, hostCourse);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST);
        }        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("bilkent/{courseId}")
    public ResponseEntity<Object> getBilkentCourseById(@Valid @PathVariable("courseId") UUID courseId) {
        try {
            BilkentCourse bilkentCourse = courseService.getBilkentCourseById(courseId);
            return Response.create("ok", HttpStatus.OK, bilkentCourse);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST);
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("host")
    public ResponseEntity<Object> getAllHostCourse() {
        try {
            List<HostCourse> hostCourses = courseService.getAllHostCourse();
            return Response.create("ok", HttpStatus.OK, hostCourses);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST);
        }        
    }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("bilkent")
    public ResponseEntity<Object> getAllBilkentCourse() {
        try {
            List<BilkentCourse> bilkentCourses = courseService.getAllBilkentCourse();
            return Response.create("ok", HttpStatus.OK, bilkentCourses);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST);
        }        
    }
    
    // @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    // @GetMapping("host/uni/{uniId}")
    // public ResponseEntity<Object> getHostCourseByUniId(@Valid @PathVariable("uniId") UUID uniId) {
    //     try {
    //         List<HostCourse> hostCourses = courseService.getHostCourseByUniId(uniId);
    //         return Response.create("ok", HttpStatus.OK, hostCourses);
    //     } catch (Exception e) {
    //         return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST);
    //     }        
    // }
    
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("bilkent/depatment/{department}")
    public ResponseEntity<Object> getBilkentCourseByDepartment(@Valid @PathVariable("department") Department department) {
        try {
            List<BilkentCourse> bilkentCourses = courseService.getBilkentCourseByDepartment(department);
            return Response.create("ok", HttpStatus.OK, bilkentCourses);
        } catch (Exception e) {
            return Response.create("university evaluations cannot be retrieved", HttpStatus.BAD_REQUEST);
        }        
    }

    // @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    // @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "approvedCourse")
    // public ResponseEntity<Object> addHostCourse(@Valid @RequestBody ApprovedCourse approvedCourse) {
    //     try {
    //         System.out.println(approvedCourse);
    //         BilkentCourse course = courseService.addApprovedCourse(approvedCourse);
    //         return Response.create("course is saved", HttpStatus.OK, course);
    //     } catch (Exception e) {
    //         return Response.create("course add failed", 499);
    //     }        
    // }

    // @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    // @GetMapping("approvedCourse/{hostUniId}")
    // public ResponseEntity<Object> getAllApprovedCoursesInUni(@Valid @PathVariable("hostUniId") UUID hostUniId) {
    //     try {
    //         List<Object> courses = courseService.getAllApprovedCoursesInUni(hostUniId);
    //         return Response.create("course is saved", HttpStatus.OK, courses);
    //     } catch (Exception e) {
    //         return Response.create("course add failed", 499);
    //     }        
    // }
}
