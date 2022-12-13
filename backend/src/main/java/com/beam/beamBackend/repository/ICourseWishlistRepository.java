package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.CourseWishlist;

@Repository
public interface ICourseWishlistRepository extends JpaRepository<CourseWishlist, UUID> {
    List<CourseWishlist> findAllByStudentId(Long studentId);
}
