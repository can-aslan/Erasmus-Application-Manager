package com.beam.beamBackend.model;

import java.util.UUID;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrimaryKeyJoinColumn;

import com.beam.beamBackend.request.HostCourseRequestBody;
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

@JsonTypeInfo(use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BilkentCourse.class, name = "bilkent_course"),
    @JsonSubTypes.Type(value = HostCourse.class, name = "host_course"),
    @JsonSubTypes.Type(value = HostCourseRequestBody.class, name = "host_course")
})

@Data
@NoArgsConstructor
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@MappedSuperclass
public abstract class Course {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID courseId;

    @NotBlank
    @Column(name = "course_code", nullable = false)
    private String courseCode;

    @NotBlank
    @Column(name = "course_name", nullable = false)
    private String courseName;
    
    @NotNull
    @Column(name = "ects", nullable = false)
    private Double ects; 

    public Course(
        @JsonProperty("id") UUID id,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("courseName") String courseName,
        @JsonProperty("ects") Double ects) {

        this.courseId = id;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.ects = ects;
    }
}
