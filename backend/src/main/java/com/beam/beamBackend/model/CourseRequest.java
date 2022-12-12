package com.beam.beamBackend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
// import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "course_request")
// @RequiredArgsConstructor
public class CourseRequest {
    @Id
    @GeneratedValue(generator = "UUID") // TODO check what happens with null
    @Column(name = "request_id")
    private final UUID requestId;

    @NotNull
    @Column(name = "student_id")
    private final Long studentId; // was type UUID before

    @NotBlank
    @Column(name = "host_code")
    private final String hostCode;

    @NotBlank
    @Column(name = "name")
    private final String name;

    @NotNull
    @Column(name = "bilkent_code")
    private final String bilkentCode;

    @NotBlank
    @Column(name = "webpage")
    private final String webpage;

    @NotBlank
    @Column(name = "syllabus_link")
    private final String syllabusLink;

    public CourseRequest(
        @JsonProperty("requestId") UUID requestId,
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("hostCode") String hostCode,
        @JsonProperty("name") String name,
        @JsonProperty("bilkentCode") String bilkentCode,
        @JsonProperty("webpage") String webpage,
        @JsonProperty("syllabusLink") String syllabusLink
    ) {
        this.requestId = (requestId == null) ? UUID.randomUUID() : requestId;
        this.studentId = studentId;
        this.hostCode = hostCode;
        this.name = name;
        this.bilkentCode = bilkentCode;
        this.webpage = webpage;
        this.syllabusLink = syllabusLink;
    }
}
