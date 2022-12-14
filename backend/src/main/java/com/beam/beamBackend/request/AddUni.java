package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.model.University;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddUni {
    @NotNull
    private String name;
    @NotNull
    private String city;
    @NotNull
    private UUID countryId;

    public AddUni(@JsonProperty("name") String name,
                @JsonProperty("city") String city,
                @JsonProperty("country_id") UUID countryId) {

        this.name = name;
        this.city = city;
        this.countryId = countryId;
    }

    public University toUniversity(AddUni uni) {
        University u = new University(null, name, city, null);

        return u;
    }
}