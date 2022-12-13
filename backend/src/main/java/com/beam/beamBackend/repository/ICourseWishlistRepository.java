package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.CourseWishlist;

@Repository
public interface ICourseWishlistRepository extends JpaRepository<CourseWishlist, UUID> {
    List<CourseWishlist> findAllByStudentId(Long studentId);
    CourseWishlist findCourseWishlistByStudentId(Long studentId);
    boolean existsByStudentId(Long studentId);

    @Modifying
    @Query("update CourseWishlist cw set cw.wishlist_items = :wishlist_items where cw.wishlist_id = :wishlist_id")
    int updateWishlistItems(@Param("wishlist_items") List<UUID> wishlistItems, @Param("wishlist_id") UUID wishlistId);
}
