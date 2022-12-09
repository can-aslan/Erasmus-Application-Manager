package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.model.CourseRequest;

public interface ICourseRequestRepository {
    boolean saveRequest(CourseRequest courseRequest);
    boolean editRequestByRequestId(UUID courseRequestId, CourseRequest newCourseRequest);
    boolean deleteRequestByRequestId(UUID courseRequestId);
    boolean courseRequestExists(UUID courseRequestId);
    List<CourseRequest> getCourseRequestsByStudentId(Long studentId);
    List<CourseRequest> getAllCourseRequests();
}
