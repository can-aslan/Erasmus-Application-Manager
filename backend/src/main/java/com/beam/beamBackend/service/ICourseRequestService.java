package com.beam.beamBackend.service;

import java.util.List;

import com.beam.beamBackend.exception.NoStudentException;
import com.beam.beamBackend.exception.NoUniversityException;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.request.CourseRequestRequestBody;

public interface ICourseRequestService {
    boolean requestCourse(CourseRequestRequestBody courseRequestBody) throws Exception, NoStudentException, NoUniversityException;
    List<CourseRequest> getAllCourseRequests() throws Exception;
    List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId) throws Exception, NoStudentException, NoUniversityException;
}
