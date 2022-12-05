package com.beam.beamBackend.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;

import com.beam.beamBackend.enums.StudentType;
import com.beam.beamBackend.service.IStudentCourseRequestService;

@WebMvcTest(controllers = StudentCourseRequestController.class)
@ContextConfiguration(classes = StudentCourseRequestController.class)
@WithMockUser(roles = {"OUTGOING"}) // Temporary user role for outgoing student
public class StudentCourseRequestControllerTests extends ControllerTestsSetup {

    @MockBean
    private IStudentCourseRequestService studentCourseRequestService;
}