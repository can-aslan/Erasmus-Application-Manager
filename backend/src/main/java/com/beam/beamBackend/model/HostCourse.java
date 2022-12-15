package com.beam.beamBackend.model;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Column;
import com.beam.beamBackend.enums.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "host_course")
public class HostCourse extends Course {
    //maybe allow null?
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "course_approval", nullable = false)
    private CourseApproval courseApproval;

    @NotBlank
    @Column(name = "syllabus", nullable = false)
    private String syllabus;
    
    @NotBlank
    @Column(name = "web_page", nullable = true)
    private String webPage;

    @NotNull
    @Column(name = "university_id", nullable = false)
    private UUID universityId;
    
    public HostCourse(
        @JsonProperty("id") UUID id,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("courseName") String courseName,
        @JsonProperty("ects") Double ects,
        @JsonProperty("syllabus") String syllabus,
        @JsonProperty("webPage") String webPage,
        @JsonProperty("universityId") UUID universityId,
        @JsonProperty("courseApproval") CourseApproval courseApproval) {
        
        super(id, courseCode, courseName, ects);
        this.syllabus = syllabus;
        this.webPage = webPage;
        this.universityId = universityId;
        this.courseApproval = courseApproval;
    }
}
