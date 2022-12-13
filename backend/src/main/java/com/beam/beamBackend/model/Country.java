package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.Continent;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "country")
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(generator = "UUID") // TODO check what happens with null
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_included_in_erasmus", nullable = false)
    private boolean isIncludedInErasmus;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "continent", nullable = false)
    private Continent continent;

    public Country(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("is_included_in_erasmus") boolean isIncludedInErasmus,
                @JsonProperty("continent") Continent continent) {

        this.id = id;
        this.name = name;
        this.isIncludedInErasmus = isIncludedInErasmus;
        this.continent = continent;
    }
    
}
