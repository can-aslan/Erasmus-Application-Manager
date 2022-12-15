package com.beam.beamBackend.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.enums.CourseWishlistStatus;
import com.beam.beamBackend.model.Wishlist;

@Repository
public interface IWishlistRepository extends JpaRepository<Wishlist, UUID> {
    
    Optional<Wishlist> findByStudentId(Long studentId);
    boolean existsByStudentId(Long studentId);

    @Modifying
    @Query("UPDATE Wishlist w SET w.status = :status WHERE w.studentId = :studentId")
    int updateWishlistItemStatus(@Param("studentId") Long studentId, @Param("status") CourseWishlistStatus status);
}
