package com.beam.beamBackend.repository;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beam.beamBackend.model.Preferences;

public interface IPreferencesRepository extends JpaRepository<Preferences, Long>{
    Optional<Preferences> findByStudentBilkentId(Long studentBilkentId);

    // Creation of preferences
}
