package com.beam.beamBackend.service;

import java.util.List;
import com.beam.beamBackend.model.CourseRequest;

public interface IStudentCourseRequestService {
    boolean requestCourse(CourseRequest courseRequest);
    List<CourseRequest> getAllCourseRequests();
    List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId);
    
    /* DEPRECATED, NOT USED
    boolean requestCourse(
        UUID studentId,
        String code,
        String name,
        String bilkentCourse,
        String webpage,
        File additionalInfo
    );
    */
}
