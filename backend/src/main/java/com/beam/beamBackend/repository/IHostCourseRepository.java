package com.beam.beamBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.HostCourse;

public interface IHostCourseRepository extends JpaRepository<HostCourse, UUID> {
    List<HostCourse> findByUniversityId(UUID universityId);
    Optional<HostCourse> findByCourseId(UUID courseId);
    boolean existsByCourseCode(String courseCode);
    // Optional<HostCourse> findByCourseId(UUID courseId);

}
