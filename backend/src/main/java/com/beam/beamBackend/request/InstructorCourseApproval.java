package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.enums.CourseRequestStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InstructorCourseApproval {

    @NotNull
    private UUID courseRequestId;

    @NotNull
    private UUID instructorId; //user id
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private CourseRequestStatus courseStatus; 

    private String feedback;
}
