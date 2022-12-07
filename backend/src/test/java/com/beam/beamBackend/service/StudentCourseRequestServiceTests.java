package com.beam.beamBackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.repository.ICourseRequestRepository;

@ExtendWith(MockitoExtension.class)
public class StudentCourseRequestServiceTests {
    @Mock
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

    @Test
    public void requestInvalidCourse() throws Exception {
        final CourseRequest courseRequest = new CourseRequest(
            null,
            (long) 22001943,
            "WRONG_COURSE_INFO",
            "Other_Course",
            "CS-319",
            "www.othercourselink.com"
        );

        Mockito.when(courseRequestRepository.saveRequest(courseRequest)).thenReturn(false);
        boolean result = studentCourseRequestService.requestCourse(courseRequest);
        assertThat(result).isFalse();
    }
}
