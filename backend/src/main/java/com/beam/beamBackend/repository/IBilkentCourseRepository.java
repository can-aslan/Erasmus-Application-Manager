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
    List<Object> findByApprovedCoursesUniversityId(UUID universityId);
    boolean existsByCourseCode(String courseCode);

    // @Query(value = "SELECT * FROM bilkent_course bil_course INNER JOIN approved_courses app_course ON INNER JOIN host_course h t1.id = t2.team_id WHERE t2.user_id = ?1 ")
    // List<BilkentCourse> findAllByUserId(long userId);
    
    // @Query("SELECT f, b FROM BilkentCourse f"
    // + " JOIN f.approvedCourses b"
    // // + "JOIN b.uni z"
    // + " WHERE b.universityId = :uniId")
    // List<Object> findApprovedByUniId(@Param("uniId") UUID uniId);
    
    // @Query(value = "SELECT *"
    // + " FROM approved_courses"
    // + " JOIN BilkentCourse b"
    // + " ON approved_courses.bilkent_course_id = b.id"
    // + " JOIN HostCourse h"
    // + " ON approved_courses.host_course_id = h.id"
    // + " WHERE h.university_id = :uniId", nativeQuery = true)
    // List<Object> findApprovedByUniId(@Param("uniId") UUID uniId);
    
    // @Query(value = "SELECT a"
    // + " FROM BilkentCourse b"
    // + " JOIN b.approvedCourses a"
    // + " ON a.bilkent_course_id = b.courseId"
    // + " JOIN HostCourse h"
    // + " ON a.host_course_id = h.courseId"
    // + " WHERE h.universityId = :uniId", nativeQuery = false)
    // List<Object> findApprovedByUniId(@Param("uniId") UUID uniId);
    
    @Query(value = "SELECT h"
    + " FROM BilkentCourse b"
    + " JOIN b.approvedCourses h"
    // + " ON a.bilkent_course_id = b.courseId"
    // + " JOIN h.universityId"
    // + " ON a.host_course_id = h.courseId"
    + " WHERE h.universityId = :uniId", nativeQuery = false)
    List<Object> findApprovedByUniId(@Param("uniId") UUID uniId);

}
