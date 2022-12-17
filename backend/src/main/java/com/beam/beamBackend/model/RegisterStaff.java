package com.beam.beamBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import com.beam.beamBackend.request.StaffRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RegisterStaff extends User {
    @Enumerated(EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Faculty faculty;

    private Set<String> bilkentCourseCodes;
}
