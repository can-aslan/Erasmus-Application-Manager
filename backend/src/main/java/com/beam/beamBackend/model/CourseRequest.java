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
    private final UUID requestId;

    @NotNull
    private final Long studentId; // was type UUID before

    @NotBlank
    private final String hostCode;

    @NotBlank
    private final String name;

    @NotNull
    private final String bilkentCode;

    @NotBlank
    private final String webpage;

    public CourseRequest(
        @JsonProperty("requestId") UUID requestId,
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("hostCode") String hostCode,
        @JsonProperty("name") String name,
        @JsonProperty("bilkentCode") String bilkentCode,
        @JsonProperty("webpage") String webpage
    ) {
        this.requestId = (requestId == null) ? UUID.randomUUID() : requestId;
        this.studentId = studentId;
        this.hostCode = hostCode;
        this.name = name;
        this.bilkentCode = bilkentCode;
        this.webpage = webpage;
    }
}
