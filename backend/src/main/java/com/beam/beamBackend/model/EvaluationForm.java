package com.beam.beamBackend.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class EvaluationForm {
    @Id
    private UUID id; //evalId

    @NotNull
    private long authorId;

    @NotNull
    private double rate;

    @NotNull
    private String comment;

    public EvaluationForm(@JsonProperty("id") UUID id,
                @JsonProperty("authorId") long authorId,
                @JsonProperty("rate") double rate,
                @JsonProperty("comment") String comment) {

        this.id = id;
        this.authorId = authorId;
        this.rate = rate;
        this.comment = comment;
    }
}