package com.beam.beamBackend.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
// import lombok.RequiredArgsConstructor;

@Entity
@Data
// @RequiredArgsConstructor
public class CourseRequest {
    @Id
    private final UUID studentId;

    @NotBlank
    private final String hostCode;

    @NotBlank
    private final String name;

    @NotNull
    private final String bilkentCode;

    @NotBlank
    private final String webpage;

    public CourseRequest(
        @JsonProperty("studentId") UUID studentId,
        @JsonProperty("hostCode") String hostCode,
        @JsonProperty("name") String name,
        @JsonProperty("bilkentCode") String bilkentCode,
        @JsonProperty("webpage") String webpage
    ) {
        this.studentId = studentId;
        this.hostCode = hostCode;
        this.name = name;
        this.bilkentCode = bilkentCode;
        this.webpage = webpage;
    }
}
