package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.enums.CourseRequestStatus;
import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.model.InstructorCourse;
import com.beam.beamBackend.model.Student;

@Repository
public interface IInstructorCourseRepository extends JpaRepository<InstructorCourse, UUID> {
    
    // returns the courses that instructor teaches
    @Query(value = "SELECT b"
    + " FROM BilkentCourse b"
    + " JOIN InstructorCourse u"
    + " ON u.bilkentCourseCode = b.courseCode"
    + " WHERE u.instructorId = :instructorId", nativeQuery = false)
    List<BilkentCourse> findInstructorCourses(@Param("instructorId") UUID instructorId);
    
    // retruns the courses that are requested by the students
    @Query(value = "SELECT r"
    + " FROM CourseRequest r"
    + " JOIN InstructorCourse u"
    + " ON u.bilkentCourseCode = r.bilkentCode"
    + " WHERE u.instructorId = :instructorId"
    + " AND r.status = :status"
    , nativeQuery = false)
    List<CourseRequest> findInstructorRequestedCourses(@Param("instructorId") UUID instructorId, @Param("status") CourseRequestStatus status);

    boolean existsByInstructorIdAndBilkentCourseCode(UUID instructorId, String bilkentCourseCode);
}
