package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beam.beamBackend.model.Country;

@Repository
@Transactional
public interface ICountryRepository extends JpaRepository<Country, UUID> {
    Country findCountryById(UUID id);
    List<Country> findCountryByIsIncludedInErasmus(boolean isIncludedInErasmus);
    boolean existsByName(String name);
}
