package com.beam.beamBackend.service;

import java.util.List;
import com.beam.beamBackend.model.CourseRequest;

public interface IStudentCourseRequestService {
    boolean requestCourse(CourseRequest courseRequest);
    List<CourseRequest> getAllCourseRequests();
    List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId);
}
