package com.beam.beamBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beam.beamBackend.model.PreApprovalForm;

@Transactional
@Repository
public interface IPreApprovalRepository extends JpaRepository<PreApprovalForm, UUID>{
    
    Optional<PreApprovalForm> findByStudentUserBilkentId(Long studentId);
    boolean existsByWishlistStudentId(Long studentId);
    List<PreApprovalForm> findAllByStudentCoordinatorUserId(UUID id);

    /*// Query should be here
    boolean insertPreApp(@Param("student_id") UUID studentId, @Param("name") String name, @Param("surname") String surname, @Param("bilkent_id") Long bilkentIdLong,
    @Param("department") Department department, @Param("uni_id") UUID uniId, @Param("academic_year") String academicYear, @Param("semester") Semester semester 
    @Param("wishlist_id") UUID wishlistId, @Param("coordinator_id") UUID coordinatorId, @Param("date") String date);*/

}
