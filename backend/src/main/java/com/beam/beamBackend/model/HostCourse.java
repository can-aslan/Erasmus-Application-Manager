package com.beam.beamBackend.model;

import java.util.Set;
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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Column;
import com.beam.beamBackend.enums.*;
import com.beam.beamBackend.request.HostCourseRequestBody;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "host_course")
public class HostCourse extends Course {
    
    @NotBlank
    @Column(name = "syllabus", nullable = false)
    private String syllabus;
    
    @NotBlank
    @Column(name = "web_page", nullable = true)
    private String webPage;

    @NotNull
    @OneToOne
    @JoinColumn(name = "uni_name", nullable = false)
    private University university;
    
    public HostCourse(
        @JsonProperty("id") UUID id,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("courseName") String courseName,
        @JsonProperty("ects") Double ects,
        @JsonProperty("syllabus") String syllabus,
        @JsonProperty("webPage") String webPage,
        @JsonProperty("uniName") University university) {
        
        super(id, courseCode, courseName, ects);
        this.syllabus = syllabus;
        this.webPage = webPage;
        this.university = university;
    }

    public static HostCourse toHostCourse(HostCourseRequestBody hostReq, University uni) {
        return new HostCourse(null, hostReq.getCourseCode(), hostReq.getCourseName(), hostReq.getEcts(), hostReq.getSyllabus(), hostReq.getWebPage(), uni);
    }
}
