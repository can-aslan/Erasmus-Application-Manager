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
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "signature")
@NoArgsConstructor
public class Signature {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "filename")
    private String key;

    public Signature(@JsonProperty("id") UUID id,
                @JsonProperty("userId") UUID userId,
                @JsonProperty("key") String key) {
        this.id = id == null ? UUID.randomUUID() : id; 
        this.userId = userId;
        this.key = key;
    }
}
