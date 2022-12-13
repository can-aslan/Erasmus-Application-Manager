package com.beam.beamBackend.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.Country;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.repository.ICountryRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import com.beam.beamBackend.request.AddUni;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UniversityCountryService {
    private final IUniversityRepository uniRepository;
    private final ICountryRepository countryRepository;

    public HashSet<University> addUniversity(AddUni[] uni) {
        HashSet<University> uniSet = new HashSet<>();
        HashSet<AddUni> removedUni = new HashSet<>();

        for (AddUni u : uni) {
            boolean uniExists = uniRepository.existsByName(u.getName());

            if (uniExists) {
                removedUni.add(u);
            } else {
                Country c = getCountry(u.getCountryId());
                University university = u.toUniversity(u);
                university.setId(UUID.randomUUID());
                university.setCountry(c);                

                uniSet.add(university);
            }
        }

        try {
            uniRepository.flush();
            uniRepository.saveAll(uniSet);
        } catch (Exception e) {
            System.out.println("uni not saved: " + removedUni);
            e.printStackTrace();
            throw e;
        }

        return uniSet;
    }

    public University getUni(UUID id) {
        try {
            return uniRepository.findUniById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<University> getUniByCountry(UUID countryId) {
        try {
            return uniRepository.findUniByCountryId(countryId);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<University> getAllUni() {
        try {
            return uniRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    public HashSet<Country> addCountry(Country[] country) {
        HashSet<Country> countrySet = new HashSet<>(Arrays.asList(country));
        HashSet<Country> removedCountry = new HashSet<>();

        for (Country u : countrySet) {
            boolean uniExists = countryRepository.existsByName(u.getName());
            System.out.println("wxists: " + uniExists);
            if (uniExists) {
                countrySet.remove(u);
                removedCountry.add(u);
            } else {
                u.setId(UUID.randomUUID());
            }
        }

        System.out.println(countrySet);

        try {
            // countryRepository.flush();
            countryRepository.saveAll(countrySet);
        } catch (Exception e) {
            System.out.println("uni not saved: " + removedCountry);
            e.printStackTrace();
            throw e;
        }

        return countrySet;
    }

    public Country getCountry(UUID id) {
        try {
            return countryRepository.findCountryById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Country> getErasmusCountry() {
        try {
            return countryRepository.findCountryByIsIncludedInErasmus(true);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Country> getNonErasmusCountry() {
        try {
            return countryRepository.findCountryByIsIncludedInErasmus(false);
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Country> getAllCountry() {
        try {
            System.out.println("himm");
            return countryRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}
