package com.beam.beamBackend.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

import com.beam.beamBackend.service.IStudentCourseRequestService;
import com.beam.beamBackend.service.StudentCourseRequestService;

import lombok.RequiredArgsConstructor;

import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.repository.ICourseRequestRepository;

public class StudentCourseRequestServiceTests {
    @Autowired
    private StudentCourseRequestService studentCourseRequestService;

    @MockBean
    private ICourseRequestRepository courseRequestRepository;

    @Test
    public void requestValidCourse() throws Exception {
        final CourseRequest courseRequest = new CourseRequest(
            null,
            (long) 22001943,
            "CS-XYZ",
            "Other_Course",
            "CS-319",
            "www.othercourselink.com"
        );

        boolean result = studentCourseRequestService.requestCourse(courseRequest);
        assertThat(result).isTrue();
    }
}
