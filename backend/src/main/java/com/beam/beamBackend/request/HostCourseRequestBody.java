package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.model.Course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HostCourseRequestBody extends Course {
    @NotBlank
    private String syllabus;
    
    @NotBlank
    private String webPage;

    @NotNull
    private UUID universityId;
}
