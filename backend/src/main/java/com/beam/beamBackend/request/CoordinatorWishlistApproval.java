package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.enums.CourseWishlistStatus;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatorWishlistApproval {
    @NonNull
    private UUID coordinatorUserId;
    @NonNull
    private Long studentBilkentId;
    @NonNull
    private CourseWishlistStatus status;

    private String feedback;
}
