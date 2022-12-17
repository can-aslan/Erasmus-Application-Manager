package com.beam.beamBackend.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.UUID;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "instructor_course")
public class InstructorCourse {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @JoinColumn(name = "instructor_id", nullable = false)
    private UUID instructorId; // user id

    @NotBlank
    @Column(name = "bilkent_course", nullable = true)
    private String bilkentCourseCode;

    @JsonIgnore
    public UUID getId() {
        return id;
    }
}
