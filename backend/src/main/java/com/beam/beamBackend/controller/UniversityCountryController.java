package com.beam.beamBackend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.exception.ExceptionLogger;
import com.beam.beamBackend.model.Country;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.request.AddUni;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IUniversityCountryService;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/general")
public class UniversityCountryController {
    private final IUniversityCountryService uniCountryService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/university")
    public ResponseEntity<Object> addUniversity(@Valid @RequestBody AddUni[] university) {
        try {
            HashSet<University> addedUnis = uniCountryService.addUniversity(university);
            return Response.create("account is created", HttpStatus.OK, addedUnis);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.CONFLICT);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/country")
    public ResponseEntity<Object> addCountry(@RequestBody Country[] country) {
        try {
            System.out.println(country[0]);
            HashSet<Country> addedCountries = uniCountryService.addCountry(country);
            return Response.create("accounts are created", HttpStatus.OK, addedCountries);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.BAD_REQUEST);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/university")
    public ResponseEntity<Object> getUniversities() {
        try {
            List<University> uniList = uniCountryService.getAllUni();
            return Response.create("ok", HttpStatus.OK, uniList);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.OK);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/country")
    public ResponseEntity<Object> getCountries(@Valid @RequestParam(name = "isErasmus") Optional<Boolean> IsErasmus) {
        try {
            List<Country> countryList;

            if (IsErasmus.isPresent()) {
                countryList = uniCountryService.getCountryByEasmus(IsErasmus.get());
            } else {
                countryList = uniCountryService.getAllCountry();
            }
            
            return Response.create("ok", HttpStatus.OK, countryList);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.OK);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/university/{uniId}")
    public ResponseEntity<Object> getUniversity(@Valid @PathVariable("uniId") UUID uniId) {
        try {
            University uni = uniCountryService.getUni(uniId);
            return Response.create("ok", HttpStatus.OK, uni);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.OK);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/country/{countryId}")
    public ResponseEntity<Object> getCountry(@Valid @PathVariable("countryId") UUID countryId) {
        try {
            Country country = uniCountryService.getCountry(countryId);
            return Response.create("ok", HttpStatus.OK, country);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.OK);  
        }        
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/country/{countryId}/university")
    public ResponseEntity<Object> getAllUniFromCountry(@Valid @PathVariable("countryId") UUID countryId) {
        try {
            List<University> universities = uniCountryService.getUniByCountry(countryId);
            return Response.create("ok", HttpStatus.OK, universities);
        } catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), HttpStatus.OK);  
        }        
    }
}
