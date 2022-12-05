package com.beam.beamBackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
// import com.beam.beamBackend.enums.StudentType;
import com.beam.beamBackend.service.IStudentCourseRequestService;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(controllers = StudentCourseRequestController.class)
@ContextConfiguration(classes = StudentCourseRequestController.class)
@WithMockUser(roles = {"OUTGOING"}) // Temporary user role for outgoing student
public class StudentCourseRequestControllerTests extends ControllerTestsSetup {

    @MockBean
    private IStudentCourseRequestService studentCourseRequestService;

    @Test
    public void requestValidCourse() throws Exception {
        this.mockMvc
            .perform(
                put("/courses/request/ed08d61c-d861-4ed4-8dc4-19299022ad44/CS-XYZ/Other_Course/CS-319/https://www.othercourselink.com")
            )
            .andExpect(status().isOk());

        verify(studentCourseRequestService).requestCourse(
            UUID.fromString("ed08d61c-d861-4ed4-8dc4-19299022ad44"),
            "CS-XYZ",
            "Other_Course",
            "CS-319",
            "https://www.othercourselink.com"
        );
    }
}