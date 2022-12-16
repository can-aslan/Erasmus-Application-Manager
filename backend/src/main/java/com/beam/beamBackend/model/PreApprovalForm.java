package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Semester;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "pre_app")

@NoArgsConstructor
@AllArgsConstructor
public class PreApprovalForm {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;


    @NotNull
    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull
    @Column(name = "bilkent_id", nullable = false)
    private Long bilkentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = false)
    private Department department;

    @NotNull
    @Column(name = "uni_id", nullable = false)
    private UUID uniId;

    @NotNull
    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = true)
    private Semester semester;
    
    @NotNull
    @Column(name = "wishlist_id", nullable = false)
    private UUID wishlistId;

    @NotNull
    @Column(name = "coordiantor_id", nullable = false)
    private UUID coordinatorId;

    @NotNull
    @Column(name = "date", nullable = false)
    private String date;

}
