package com.beam.beamBackend.request;

import lombok.AllArgsConstructor;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.beam.beamBackend.enums.CourseRequestDestination;
import com.beam.beamBackend.enums.CourseRequestStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
// import lombok.RequiredArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestRequestBody {
    @NotNull
    private Long studentId; // was type UUID before

    @NotBlank
    private String hostCode;

    @NotNull
    private Double hostEcts;

    @NotBlank
    private String name;

    @NotNull
    private String bilkentCode;

    @NotBlank
    private String webpage;

    @NotBlank
    private String syllabusLink;

    @Enumerated(EnumType.STRING)
    private CourseRequestDestination destination;

    @Enumerated(EnumType.STRING)
    private CourseRequestStatus status;
}
