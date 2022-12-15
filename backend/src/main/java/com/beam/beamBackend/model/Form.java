package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.FormEnum;
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
@Table(name = "form")
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id")
    private UUID id;

    @NotNull
    @Column(name = "user_id")
    private UUID userId;

    @NotNull
    @Column(name = "form_type")
    private FormEnum formType;

    @NotNull
    @Column(name = "filename")
    private String key;

    public Form(@JsonProperty("uuid") UUID id,
                @JsonProperty("userUuid") UUID userId,
                @JsonProperty("fileType") FormEnum fileType,
                @JsonProperty("fileName") String key ) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.userId = userId;
        this.formType = fileType;
        this.key = key;
    }
    
}
