package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.FormEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "form")
public class Form {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "form_uuid")
    private UUID id;

    @NotNull
    @Column(name = "user_uuid")
    private UUID userId;

    @NotNull
    @Column(name = "form_type")
    private FormEnum formType;

    @NotNull
    @Column(name = "filename")
    private String key;

    public Form(@JsonProperty("uuid") UUID formUuid,
                @JsonProperty("userUuid") UUID userUuid,
                @JsonProperty("fileType") FormEnum fileType,
                @JsonProperty("fileName") String key ) {
        this.id = formUuid == null ? UUID.randomUUID() : formUuid;;
        this.userId = userUuid;
        this.formType = fileType;
        this.key = key;
    }
    
}
