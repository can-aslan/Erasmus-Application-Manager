package com.beam.beamBackend.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
public class UniversityCountryService implements IUniversityCountryService {
    private final IUniversityRepository uniRepository;
    private final ICountryRepository countryRepository;

    @Override
    public HashSet<University> addUniversity(AddUni[] uni) throws Exception {
        HashSet<University> uniSet = new HashSet<>();
        HashSet<AddUni> removedUni = new HashSet<>();

        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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

    @Override
    public University getUni(UUID id) throws Exception {
        try {
            Optional<University> uni = uniRepository.findUniById(id);

            if (!uni.isPresent()) {
                throw new Exception("uni not found");
            }

            return uni.get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<University> getUniByCountry(UUID countryId) throws Exception {
        try {
            boolean countryExist = countryRepository.existsById(countryId);

            if (!countryExist) {
                throw new Exception("country is not found");
            }

            List<University> uni = uniRepository.findUniByCountryId(countryId);

            return uni;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<University> getAllUni() {
        try {
            System.out.println("himm");
            return uniRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
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

    @Override
    public Country getCountry(UUID id) throws Exception {
        try {
            Optional<Country> country = countryRepository.findCountryById(id);

            if (!country.isPresent()) {
                throw new Exception("country not found");
            }

            return countryRepository.findCountryById(id).get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Country> getCountryByEasmus(boolean isIncludedInErasmus) {
        try {
            return countryRepository.findCountryByIsIncludedInErasmus(isIncludedInErasmus);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Country> getAllCountry() {
        try {
            
            return countryRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}
