package com.beam.beamBackend.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import com.beam.beamBackend.enums.*;

@Entity
@Data
public abstract class Course {
    @Id
    private final UUID courseUUID;

    @NotBlank
    private final String courseCode;

    @NotBlank
    private final String coursename;

    @NotNull
    private final Department department;

    @NotNull
    private final Long courseID;
    
    @NotNull
    private final Double ects;

    @NotBlank
    private final String syllabus; // shouldn't it be a file? yes probably, or a link
    
    @NotNull
    private final Long universityID;

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
