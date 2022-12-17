package com.beam.beamBackend.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.enums.FormEnum;
import com.beam.beamBackend.model.Form;

import jakarta.transaction.Transactional;

@Repository
public interface IFormRepository extends JpaRepository<Form, UUID> {
    @Transactional
    Optional<Form> findFormByUserIdAndFormType(UUID id, FormEnum formType);

    @Transactional
    void deleteByUserId(UUID id);
}
