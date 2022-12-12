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
        courseRequestRepository.save(courseRequest);
        return true;
    }

    @Override
    public List<CourseRequest> getAllCourseRequests() {
        return courseRequestRepository.findAll();
    }

    @Override
    public List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId) {
        return courseRequestRepository.findAllByStudentID(studentId);
    }
    
}
