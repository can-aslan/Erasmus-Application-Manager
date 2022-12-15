package com.beam.beamBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.beam.beamBackend.enums.CourseWishlistStatus;

@Entity
@Data
@Table(name = "wishlist")
@NoArgsConstructor
public class Wishlist {
    @Id
    @NotNull
    @Column(name = "student_id")
    private Long studentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourseWishlistStatus status;

    public Wishlist(
        @JsonProperty("studentId") Long studentId,
        @JsonProperty("status") CourseWishlistStatus status
    ) {
        this.studentId = studentId;
        this.status = (status == null) ? CourseWishlistStatus.WAITING : status;
    }
}