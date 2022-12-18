package com.beam.beamBackend.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.exception.StudentNotFound;
import com.beam.beamBackend.exception.UniversityNotFound;
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
    public boolean requestCourse(CourseRequestRequestBody courseRequestBody) throws Exception, StudentNotFound, UniversityNotFound {
        try {
            Student student = getStudentByCheckingIdAndHostUniName(courseRequestBody.getStudentId());
            String hostUniName = student.getHostUni().getName();

            CourseRequest courseRequest = CourseRequest.toCourseRequest(courseRequestBody, hostUniName);
            courseRequestRepository.save(courseRequest);
            return true;
        }
        catch (StudentNotFound studentNotFoundException) {
            studentNotFoundException.printStackTrace();
            throw studentNotFoundException;
        }
        catch (UniversityNotFound universityNotFoundException) {
            universityNotFoundException.printStackTrace();
            throw universityNotFoundException;
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
    public List<CourseRequest> getAllCourseRequestsOfStudent(Long studentId) throws Exception, StudentNotFound, UniversityNotFound {
        try {
            getStudentByCheckingIdAndHostUniName(studentId);

            return courseRequestRepository.findAllByStudentId(studentId);
        }
        catch (StudentNotFound studentNotFoundException) {
            studentNotFoundException.printStackTrace();
            throw studentNotFoundException;
        }
        catch (UniversityNotFound universityNotFoundException) {
            universityNotFoundException.printStackTrace();
            throw universityNotFoundException;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Student getStudentByCheckingIdAndHostUniName(Long studentId) throws StudentNotFound, UniversityNotFound {
        Optional<Student> student = studentRepository.findByUserBilkentId(studentId);

        if (!student.isPresent()) {
            throw new StudentNotFound("user not found");
        }

        String hostUniName = student.get().getHostUni().getName();

        if (hostUniName.isBlank()) {
            throw new UniversityNotFound("host university not found");
        }

        return student.get();
    }
}
