package com.beam.beamBackend.model;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;

import com.beam.beamBackend.enums.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "host_course")
public class OtherUniCourse extends Course {
    
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
    
    public OtherUniCourse(
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
