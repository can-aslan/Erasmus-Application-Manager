package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.WishlistItem;

@Repository
public interface IWishlistItemRepository extends JpaRepository<WishlistItem, UUID> {
    List<WishlistItem> findAllByStudentId(Long studentId);
}
