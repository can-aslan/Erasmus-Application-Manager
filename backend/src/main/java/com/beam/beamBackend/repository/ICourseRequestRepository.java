package com.beam.beamBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.CourseRequest;

@Repository
public interface ICourseRequestRepository extends JpaRepository<CourseRequest, UUID> {
    List<CourseRequest> findAllByStudentId(Long studentId);
    Optional<CourseRequest> findByRequestId(UUID requestId);
    boolean existsByHostCodeAndBilkentCode(String hostCode, String bilkentCode);
    /*
    boolean saveRequest(CourseRequest courseRequest);
    boolean editRequestByRequestId(UUID courseRequestId, CourseRequest newCourseRequest);
    boolean deleteRequestByRequestId(UUID courseRequestId);
    boolean courseRequestExists(UUID courseRequestId);
    List<CourseRequest> getCourseRequestsByStudentId(Long studentId);
    List<CourseRequest> getAllCourseRequests();
    */
}
