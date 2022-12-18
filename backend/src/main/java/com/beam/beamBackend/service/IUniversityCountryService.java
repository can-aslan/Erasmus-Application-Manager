package com.beam.beamBackend.service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.model.Country;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.request.AddUni;

public interface IUniversityCountryService {
    HashSet<University> addUniversity(AddUni[] uni) throws Exception;
    University getUni(UUID id) throws Exception;
    List<University> getUniByCountry(UUID countryId) throws Exception;
    List<University> getAllUni();
    HashSet<Country> addCountry(Country[] country);
    Country getCountry(UUID id) throws Exception;
    List<Country> getCountryByEasmus(boolean isIncludedInErasmus);
    List<Country> getAllCountry();
}
