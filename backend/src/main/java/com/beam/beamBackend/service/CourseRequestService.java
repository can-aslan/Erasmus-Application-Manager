package com.beam.beamBackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.repository.ICourseRequestRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.request.CourseRequestRequestBody;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseRequestService implements ICourseRequestService {

    private final ICourseRequestRepository courseRequestRepository;
    private final IStudentRepository studentRepository;

    @Override
    public boolean requestCourse(CourseRequestRequestBody courseRequestBody) throws Exception {
        try {
            Optional<Student> student = studentRepository.findByUserBilkentId(courseRequestBody.getStudentId());

            if (!student.isPresent()) {
                throw new Exception("user not found");
            }

            String hostUniName = student.get().getHostUni().getName();

            if (hostUniName == null) {
                throw new Exception("host university not found");
            }

            CourseRequest courseRequest = CourseRequest.toCourseRequest(courseRequestBody, hostUniName);
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
