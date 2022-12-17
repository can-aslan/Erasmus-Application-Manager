package com.beam.beamBackend.request;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InstructorCourseAdd {
    @NotNull
    private UUID instructorId;

    @NotBlank
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private Set<String> bilkentCourseCode;
}
