package com.beam.beamBackend.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "signature")
public class Signature {

    @Id
    @NotNull
    @Column(name = "userUuid")
    private UUID userId;

    @NotNull
    @Column(name = "filename")
    private String key;

    public Signature(@JsonProperty("name") UUID userId,
                @JsonProperty("city") String key) {
        this.userId = userId;
        this.key = key;
    }
}
