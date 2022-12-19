package com.beam.beamBackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
// import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import com.beam.beamBackend.enums.CourseRequestDestination;
import com.beam.beamBackend.enums.CourseRequestStatus;
import com.beam.beamBackend.model.CourseRequest;
// import com.beam.beamBackend.enums.StudentType;
import com.beam.beamBackend.service.ICourseRequestService;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CourseRequestController.class)
@ContextConfiguration(classes = CourseRequestController.class)
// @WithMockUser(roles = {"OUTGOING"}) // Temporary user role for outgoing student
public class StudentCourseRequestControllerTests extends ControllerTestsSetup {

    @MockBean
    private ICourseRequestService studentCourseRequestService;

    @Test
    public void requestValidCourse() throws Exception {
        final CourseRequest courseRequest = new CourseRequest(
            null,
            (long) 22001943,
            "CS-XYZ",
            5.5,
            "Other_Course",
            "CS-319",
            "www.othercourselink.com",
            "www.syllabus.com",
            CourseRequestDestination.COORDINATOR,
            CourseRequestStatus.PENDING,
            "Host University",
            "feedback"
        );
        
        // when(studentCourseRequestService.requestCourse(courseRequest)).thenReturn(true);
        
        this.mockMvc
            .perform(
                post("/api/v1/course/student/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{"
                    +     "\"requestId\": \"" + courseRequest.getRequestId().toString() + "\","
                    +     "\"studentId\": " + courseRequest.getStudentId().toString() + ","
                    +     "\"hostCode\": \"" + courseRequest.getHostCode() + "\","
                    +     "\"hostEcts\": " + courseRequest.getHostEcts() + ","
                    +     "\"name\": \"" + courseRequest.getName() + "\","
                    +     "\"bilkentCode\": \"" + courseRequest.getBilkentCode() + "\","
                    +     "\"webpage\": \"" + courseRequest.getWebpage() + "\","
                    +     "\"syllabusLink\": \"" + courseRequest.getSyllabusLink() + "\","
                    +     "\"destination\": \"" + courseRequest.getDestination() + "\","
                    +     "\"status\": \"" + courseRequest.getStatus() + "\","
                    +     "\"hostUniName\": \"" + courseRequest.getHostUniName() + "\","
                    +     "\"feedback\": \"" + courseRequest.getFeedback() + "\""
                    + "}"
                )
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("course requested")
        );
        
        // verify(studentCourseRequestService).requestCourse(courseRequest);
    }

    // Course Request with blank fields also generates BadRequest, but from directly Spring.
    @Test
    public void requestInvalidCourse() throws Exception {
        final CourseRequest courseRequest = new CourseRequest(
            null,
            (long) 22001943,
            "CS-XYZ",
            5.5,
            "Other_Course",
            "CS-319",
            "www.othercourselink.com",
            "www.syllabus.com",
            CourseRequestDestination.COORDINATOR,
            CourseRequestStatus.PENDING,
            "Host University",
            "feedback"
        );
        
        // when(studentCourseRequestService.requestCourse(courseRequest)).thenReturn(false);
        
        this.mockMvc
            .perform(
                post("/api/v1/course/student/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{"
                    +     "\"requestId\": \"" + courseRequest.getRequestId().toString() + "\","
                    +     "\"studentId\": " + courseRequest.getStudentId().toString() + ","
                    +     "\"hostCode\": \"" + courseRequest.getHostCode() + "\","
                    +     "\"hostEcts\": " + courseRequest.getHostEcts() + ","
                    +     "\"name\": \"" + courseRequest.getName() + "\","
                    +     "\"bilkentCode\": \"" + courseRequest.getBilkentCode() + "\","
                    +     "\"webpage\": \"" + courseRequest.getWebpage() + "\","
                    +     "\"syllabusLink\": \"" + courseRequest.getSyllabusLink() + "\","
                    +     "\"destination\": \"" + courseRequest.getDestination() + "\","
                    +     "\"status\": \"" + courseRequest.getStatus() + "\","
                    +     "\"hostUniName\": \"" + courseRequest.getHostUniName() + "\","
                    +     "\"feedback\": \"" + courseRequest.getFeedback() + "\""
                    + "}"
                )
            )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("course request failed")
        );
        
        // verify(studentCourseRequestService).requestCourse(courseRequest);
    }
}