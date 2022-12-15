package com.beam.beamBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beam.beamBackend.model.University;

@Repository
@Transactional
public interface IUniversityRepository extends JpaRepository<University, UUID> {
    Optional<University> findUniById(UUID id);
    List<University> findUniByCountryId(UUID countryId);
    boolean existsByName(String name);
    University findUniByName(String name);
}
