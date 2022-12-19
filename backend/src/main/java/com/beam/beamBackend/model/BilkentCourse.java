package com.beam.beamBackend.model;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.beam.beamBackend.enums.*;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "bilkent_course")
public class BilkentCourse extends Course {

    @NotNull
    @Column(name = "bilkent_credit", nullable = false)
    private Double bilkentCredit;

    @NotNull
    @Column(name = "department", nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;
    
    public BilkentCourse(
        @JsonProperty("id") UUID id,
        @JsonProperty("courseCode") String courseCode,
        @JsonProperty("courseName") String courseName,
        @JsonProperty("ects") Double ects,
        @JsonProperty("bilkentCredit") Double bilkentCredit,
        @JsonProperty("department") Department department) {

        super(id, courseCode, courseName, ects);
        this.department = department;
        this.bilkentCredit = bilkentCredit;
    }
}

    
