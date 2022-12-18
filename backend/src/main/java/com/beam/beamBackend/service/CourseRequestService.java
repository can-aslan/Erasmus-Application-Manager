package com.beam.beamBackend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.repository.ICourseRequestRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseRequestService implements ICourseRequestService {

    private final ICourseRequestRepository courseRequestRepository;

    @Override
    public boolean requestCourse(CourseRequest courseRequest) {
        try {
            courseRequestRepository.save(courseRequest);
            return true;
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CourseRequest> getAllCourseRequests() {
        try {
            return courseRequestRepository.findAll();
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId) {
        try {
            return courseRequestRepository.findAllByStudentId(studentId);
        }
        catch (Exception e) {
            throw e;
        }
    }
    
}
