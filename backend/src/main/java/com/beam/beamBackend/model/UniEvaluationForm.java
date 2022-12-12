package com.beam.beamBackend.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.beam.beamBackend.enums.EvalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper=true)
public class UniEvaluationForm extends EvaluationForm {
    // @Id
    // private UUID id; //evalId

    // @NotNull
    // private long authorId;

    // @NotNull
    // private double rate;

    // @NotBlank
    // private String comment;

    @NotNull
    private UUID uniId; //courseId

    public UniEvaluationForm(@JsonProperty("id") UUID id,
                @JsonProperty("authorId") long authorId,
                @JsonProperty("rate") double rate,
                @JsonProperty("comment") String comment,
                @JsonProperty("uniId") UUID uniId,
                @JsonProperty("evalStatus") EvalStatus evalStatus) {
        super(id, authorId, rate, comment, evalStatus);    
        this.uniId = uniId;
    }    
}
