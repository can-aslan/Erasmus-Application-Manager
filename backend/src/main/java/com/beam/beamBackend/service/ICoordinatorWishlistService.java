package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.request.CoordinatorWishlistApproval;

import jakarta.validation.Valid;

public interface ICoordinatorWishlistService {
    ArrayList<Wishlist> getCoordinatorsWishlists(@Valid UUID coordinatorId) throws Exception;
    Optional<Wishlist> getWishlist(@Valid UUID coordinatorId, @Valid Long studentId) throws Exception;
    Wishlist determineWishlistStatus(@Valid CoordinatorWishlistApproval wishlistResult) throws Exception;
}
