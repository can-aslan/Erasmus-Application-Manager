package com.beam.beamBackend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@Entity
@Builder
@Jacksonized
@Table(name = "university")
@NoArgsConstructor
public class University {
    @Id
    @GeneratedValue(generator = "UUID") // TODO check what happens with null
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "info", nullable = false)
    private String info;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country countryId;

    public University(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("city") String city,
                @JsonProperty("info") String info,
                @JsonProperty("country_id") Country countryId) {

        this.id = id;
        this.name = name;
        this.city = city;
        this.info = info;
        this.countryId = countryId;
    }
}
