package com.beam.beamBackend.model;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;

import jakarta.persistence.Entity;
import lombok.Data;

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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "department", nullable = true)
    private Department department;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "faculty", nullable = true)
    private Faculty faculty;

    public static Staff toStaff(StaffRequest sReq, User u) {
        return new Staff(null, u, sReq.getDepartment(), sReq.getFaculty());
    }
}
