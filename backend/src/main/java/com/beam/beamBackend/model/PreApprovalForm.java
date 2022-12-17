package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Semester;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @Column(name = "preApp_id", nullable = false)
    private UUID id;

    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student", nullable = false)
    private Student student;
    
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist", nullable = false)
    private Wishlist wishlist;

    @NotNull
    @Column(name = "date", nullable = false)
    private String date;
}
