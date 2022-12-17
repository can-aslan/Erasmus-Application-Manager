package com.beam.beamBackend.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InstructorCourseAdd {
    @NotNull
    private UUID instructorId;
    @NotBlank
    private String bilkentCourseCode;
}
