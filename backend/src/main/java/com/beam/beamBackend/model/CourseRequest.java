package com.beam.beamBackend.model;

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

@Entity
@Data
@Table(name = "course_request")
// @RequiredArgsConstructor
@NoArgsConstructor
public class CourseRequest {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "request_id")
    private UUID requestId;

    @NotNull
    @Column(name = "student_id")
    private Long studentId; // was type UUID before

    @NotBlank
    @Column(name = "host_code")
    private String hostCode;

    @NotNull
    @Column(name = "host_ects")
    private Double hostEcts;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "bilkent_code")
    private String bilkentCode;

    @NotBlank
    @Column(name = "webpage")
    private String webpage;

    @NotBlank
    @Column(name = "syllabus_link")
    private String syllabusLink;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "destination")
    private CourseRequestDestination destination;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseRequestStatus status;

    public CourseRequest (
        @JsonProperty("requestId") UUID requestId,
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("hostCode") String hostCode,
        @JsonProperty("hostEcts") Double hostEcts,
        @JsonProperty("name") String name,
        @JsonProperty("bilkentCode") String bilkentCode,
        @JsonProperty("webpage") String webpage,
        @JsonProperty("syllabusLink") String syllabusLink,
        @JsonProperty("destination") CourseRequestDestination destination,
        @JsonProperty("status") CourseRequestStatus status
    ) {
        this.requestId = (requestId == null) ? UUID.randomUUID() : requestId;
        this.studentId = studentId;
        this.hostEcts = hostEcts;
        this.hostCode = hostCode;
        this.name = name;
        this.bilkentCode = bilkentCode;
        this.webpage = webpage;
        this.syllabusLink = syllabusLink;
        this.destination = (destination == null) ? CourseRequestDestination.COORDINATOR : destination;
        this.status = (status == null) ? CourseRequestStatus.PENDING : status;
    }
}
