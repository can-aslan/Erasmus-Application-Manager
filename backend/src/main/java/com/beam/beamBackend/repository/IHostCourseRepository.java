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
    Optional<HostCourse> findByCourseId(UUID courseId);
    List<HostCourse> findByUniName(String uniName);
    List<HostCourse> findByCourseIdIn(List<UUID> courseIdList);
    boolean existsByCourseCode(String courseCode);
    boolean existsByCourseCodeAndUniName(String courseCode, String uniName);
    // Optional<HostCourse> findByCourseId(UUID courseId);

    // @Query(value = "SELECT * FROM team t1 INNER JOIN user_team t2 ON t1.id = t2.team_id WHERE t2.user_id = ?1 ")
    // List<HostCourse> findAllByUserId(long userId);
}
