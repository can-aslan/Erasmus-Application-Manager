package com.beam.beamBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.WishlistItem;

@Repository
public interface IWishlistItemRepository extends JpaRepository<WishlistItem, UUID> {
    List<WishlistItem> findAllByStudentId(Long studentId);
    Optional<WishlistItem> findByStudentIdAndBilkentCourse(Long studentId, String bilkentCourse);
    boolean deleteByStudentIdAndBilkentCourse(Long studentId, String bilkentCourse);
}
