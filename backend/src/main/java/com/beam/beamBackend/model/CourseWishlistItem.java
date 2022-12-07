package com.beam.beamBackend.model;

import java.util.UUID;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.beam.beamBackend.enums.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CourseWishlistItem extends Course {
    
    @NotBlank
    private final String semester;

    public CourseWishlistItem(
        @JsonProperty("courseUUID") UUID courseUUID,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("coursename") String coursename,
        @JsonProperty("department") Department department,
        @JsonProperty("courseID") Long courseID,
        @JsonProperty("ects") Double ects,
        @JsonProperty("syllabus") String syllabus,
        @JsonProperty("universityID") Long universityID,
        @JsonProperty("semester") String semester
    ) {
        super(courseUUID, courseCode, coursename, department, courseID, ects, syllabus, universityID);
        this.semester = semester;
    }
}
