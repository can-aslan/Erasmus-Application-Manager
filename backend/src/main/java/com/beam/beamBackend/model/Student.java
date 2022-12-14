package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    UUID id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "department1", nullable = false)
    Department department;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "faculty1", nullable = false)
    Faculty faculty;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "department2", nullable = true)
    Department department2;

    @Enumerated(EnumType.STRING)
    @Column(name = "faculty2", nullable = true)
    Faculty faculty2;

    @NotBlank
    @Size(min = 8)
    @Column(name = "telephone", nullable = false)
    String telephoneNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "study_type", nullable = false)
    StudyType studyType;

    @NotBlank
    @Column(name = "nationality", nullable = false)
    String nationality;

    @NotBlank
    @Column(name = "dateOfBirth", nullable = false)
    String dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    Sex sex;
}