package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.beam.beamBackend.enums.CourseWishlistStatus;
import com.beam.beamBackend.model.Wishlist;

import jakarta.validation.Valid;

public interface ICoordinatorWishlistService {
    ArrayList<Wishlist> getCoordinatorsWishlists(@Valid UUID coordinatorId) throws Exception;
    Optional<Wishlist> getWishlist(@Valid UUID coordinatorId, @Valid Long studentId) throws Exception;
    void determineWishlistStatus(@Valid UUID coordinatorId, @Valid Long studentId, @Valid CourseWishlistStatus status) throws Exception;
}
