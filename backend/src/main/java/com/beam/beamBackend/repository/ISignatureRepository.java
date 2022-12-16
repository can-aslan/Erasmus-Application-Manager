package com.beam.beamBackend.repository;

import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.Form;
import com.beam.beamBackend.model.Signature;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ISignatureRepository extends JpaRepository<Signature, UUID> {
    // Signature findSignatureByUserId();
}
