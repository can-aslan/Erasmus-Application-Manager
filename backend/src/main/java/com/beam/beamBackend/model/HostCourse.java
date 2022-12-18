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
    
    @NotBlank
    @Column(name = "syllabus", nullable = false)
    private String syllabus;
    
    @NotBlank
    @Column(name = "web_page", nullable = true)
    private String webPage;

    @NotNull
    @Column(name = "uni_name", nullable = false)
    private String uniName;
    
    public HostCourse(
        @JsonProperty("id") UUID id,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("courseName") String courseName,
        @JsonProperty("ects") Double ects,
        @JsonProperty("syllabus") String syllabus,
        @JsonProperty("webPage") String webPage,
        @JsonProperty("uniName") String uniName) {
        
        super(id, courseCode, courseName, ects);
        this.syllabus = syllabus;
        this.webPage = webPage;
        this.uniName = uniName;
    }
}
