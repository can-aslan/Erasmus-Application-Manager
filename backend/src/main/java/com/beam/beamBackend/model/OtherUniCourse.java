package com.beam.beamBackend.model;

import java.util.UUID;
import jakarta.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beam.beamBackend.enums.*;

// @Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class OtherUniCourse extends Course {
    
    @NotNull
    private final CourseApproval courseApproval;
    
    public OtherUniCourse(
        @JsonProperty("courseUUID") UUID courseUUID,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("coursename") String coursename,
        @JsonProperty("department") Department department,
        @JsonProperty("courseID") Long courseID,
        @JsonProperty("ects") Double ects,
        @JsonProperty("syllabus") String syllabus,
        @JsonProperty("universityID") Long universityID,
        @JsonProperty("courseApproval") CourseApproval courseApproval
    ) {
        super(courseUUID, courseCode, coursename, department, courseID, ects, syllabus, universityID);
        this.courseApproval = courseApproval;
    }
}
