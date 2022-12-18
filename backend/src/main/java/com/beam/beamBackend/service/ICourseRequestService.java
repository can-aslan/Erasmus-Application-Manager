package com.beam.beamBackend.service;

import java.util.List;
import com.beam.beamBackend.exception.StudentNotFound;
import com.beam.beamBackend.exception.UniversityNotFound;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.request.CourseRequestRequestBody;

public interface ICourseRequestService {
    boolean requestCourse(CourseRequestRequestBody courseRequestBody) throws Exception, StudentNotFound, UniversityNotFound;
    List<CourseRequest> getAllCourseRequests() throws Exception;
    List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId) throws Exception, StudentNotFound, UniversityNotFound;
}
