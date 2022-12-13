package com.beam.beamBackend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.enums.FormEnum;
import com.beam.beamBackend.model.Form;

@Repository
public interface IFormRepository extends JpaRepository<Form, UUID> {
    Form findFormByIdAndFormType(UUID id, FormEnum formType);
}
