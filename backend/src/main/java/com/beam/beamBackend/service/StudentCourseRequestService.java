package com.beam.beamBackend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.repository.ICourseRequestRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentCourseRequestService implements IStudentCourseRequestService {

    private final ICourseRequestRepository courseRequestRepository;

    @Override
    public boolean requestCourse(CourseRequest courseRequest) {
        return courseRequestRepository.saveRequest(courseRequest);
    }

    @Override
    public List<CourseRequest> getAllCourseRequests() {
        return courseRequestRepository.getAllCourseRequests();
    }

    @Override
    public List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId) {
        return courseRequestRepository.getCourseRequestsByStudentId(studentId);
    }
    
}
