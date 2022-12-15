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

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.HostCourse;

public interface IBilkentCourseRepository extends JpaRepository<BilkentCourse, UUID> {
    Optional<BilkentCourse> findByCourseId(UUID courseId);
    List<BilkentCourse> findByDepartment(Department department);
    boolean existsByCourseCode(String courseCode);
}
