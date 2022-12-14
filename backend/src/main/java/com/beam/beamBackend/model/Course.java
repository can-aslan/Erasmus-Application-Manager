package com.beam.beamBackend.model;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.beam.beamBackend.enums.*;

@Entity
@Data
@NoArgsConstructor
public class Course {
    @Id
    private UUID courseUUID;

    @NotBlank
    private String courseCode;

    @NotBlank
    private String coursename;

    @NotNull
    private Department department;

    @NotNull
    private Long courseID;
    
    @NotNull
    private Double ects;

    @NotBlank
    private String syllabus; // shouldn't it be a file? yes probably, or a link
    
    @NotNull
    private Long universityID;

    public Course(
        @JsonProperty("courseUUID") UUID courseUUID,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("coursename") String coursename,
        @JsonProperty("department") Department department,
        @JsonProperty("courseID") Long courseID,
        @JsonProperty("ects") Double ects,
        @JsonProperty("syllabus") String syllabus,
        @JsonProperty("universityID") Long universityID
    ) {
        this.courseUUID = (courseUUID == null) ? UUID.randomUUID() : courseUUID;
        this.courseCode = courseCode;
        this.coursename = coursename;
        this.department = department;
        this.courseID = courseID;
        this.ects = ects;
        this.syllabus = syllabus;
        this.universityID = universityID;
    }
}
