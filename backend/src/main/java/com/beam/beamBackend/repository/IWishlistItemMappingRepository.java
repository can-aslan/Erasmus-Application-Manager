package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.WishlistItemMapping;

@Repository
public interface IWishlistItemMappingRepository extends JpaRepository<WishlistItemMapping, UUID> {
    List<WishlistItemMapping> findAllByWishlistItemId(UUID wishlistItemId);
}
