package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.enums.CourseWishlistStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorWishlistApproval {
    @NotNull
    private UUID coordinatorUserId;

    @NotNull
    private Long studentBilkentId;

    @NotNull
    private CourseWishlistStatus status;

    private String feedback;
}
