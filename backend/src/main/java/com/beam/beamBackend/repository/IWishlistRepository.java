package com.beam.beamBackend.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.Wishlist;

@Repository
@Deprecated
public interface IWishlistRepository extends JpaRepository<Wishlist, UUID> {
    Optional<Wishlist> findByStudentId(Long studentId);
}
