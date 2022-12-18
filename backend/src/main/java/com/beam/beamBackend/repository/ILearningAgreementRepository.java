package com.beam.beamBackend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beam.beamBackend.model.LearningAgreementForm;

public interface ILearningAgreementRepository extends JpaRepository<LearningAgreementForm, UUID> {
    Optional<LearningAgreementForm> findByStudentId(Long studentId);
}
