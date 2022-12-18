package com.beam.beamBackend.service;

import java.util.List;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.request.CourseRequestRequestBody;

public interface IStudentCourseRequestService {
    boolean requestCourse(CourseRequestRequestBody courseRequest) throws Exception;
    List<CourseRequest> getAllCourseRequests();
    List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId);
}
