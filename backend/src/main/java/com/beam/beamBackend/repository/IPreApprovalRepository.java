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
public interface IPreApprovalRepository extends JpaRepository<PreApprovalForm, UUID> {

    Optional<PreApprovalForm> findByStudentUserBilkentId(Long studentId);
    boolean existsByWishlistStudentId(Long studentId);
    List<PreApprovalForm> findByStudentCoordinatorUserId(UUID id);
}
