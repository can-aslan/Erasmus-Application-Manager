package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.CourseWishlistItem;

@Repository
public interface ICourseWishlistItemRepository extends JpaRepository<CourseWishlistItem, UUID> {
    List<CourseWishlistItem> findAllByStudentId(Long studentId);
    boolean existsByStudentId(Long studentId);
}
