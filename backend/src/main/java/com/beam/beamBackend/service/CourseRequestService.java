package com.beam.beamBackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.exception.NoStudentException;
import com.beam.beamBackend.exception.NoUniversityException;
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
    public boolean requestCourse(CourseRequestRequestBody courseRequestBody) throws Exception, NoStudentException, NoUniversityException {
        try {
            Student student = getStudentByCheckingIdAndHostUniName(courseRequestBody.getStudentId());
            String hostUniName = student.getHostUni().getName();

            CourseRequest courseRequest = CourseRequest.toCourseRequest(courseRequestBody, hostUniName);
            courseRequestRepository.save(courseRequest);
            return true;
        }
        catch (NoStudentException noStudentException) {
            noStudentException.printStackTrace();
            throw noStudentException;
        }
        catch (NoUniversityException noUniversityException) {
            noUniversityException.printStackTrace();
            throw noUniversityException;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<CourseRequest> getAllCourseRequests() throws Exception {
        try {
            return courseRequestRepository.findAll();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId) throws Exception, NoStudentException, NoUniversityException {
        try {
            getStudentByCheckingIdAndHostUniName(studentId);

            return courseRequestRepository.findAllByStudentId(studentId);
        }
        catch (NoStudentException noStudentException) {
            noStudentException.printStackTrace();
            throw noStudentException;
        }
        catch (NoUniversityException noUniversityException) {
            noUniversityException.printStackTrace();
            throw noUniversityException;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Student getStudentByCheckingIdAndHostUniName(Long studentId) throws NoStudentException, NoUniversityException {
        Optional<Student> student = studentRepository.findByUserBilkentId(studentId);

        if (!student.isPresent()) {
            throw new NoStudentException("user not found");
        }

        String hostUniName = student.get().getHostUni().getName();

        if (hostUniName.isBlank()) {
            throw new NoUniversityException("host university not found");
        }

        return student.get();
    }
}
