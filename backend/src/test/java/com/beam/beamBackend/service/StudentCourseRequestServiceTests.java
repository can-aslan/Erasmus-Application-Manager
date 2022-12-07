package com.beam.beamBackend.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.repository.ICourseRequestRepository;

public class StudentCourseRequestServiceTests {
    @MockBean
    private ICourseRequestRepository courseRequestRepository;

    @InjectMocks
    private StudentCourseRequestService studentCourseRequestService;

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

        Mockito.when(courseRequestRepository.saveRequest(courseRequest)).thenReturn(true);
        boolean result = studentCourseRequestService.requestCourse(courseRequest);
        assertThat(result).isTrue();
    }
}
