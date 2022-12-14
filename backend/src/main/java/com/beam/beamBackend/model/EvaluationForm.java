package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;

import lombok.NoArgsConstructor;

@Data
// @Entity
// @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@MappedSuperclass
public abstract class EvaluationForm {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    UUID id; //evalId

    @NotNull
    @Column(name = "author_id", nullable = false, unique = true)
    Long authorId;

    @NotNull
    @Column(name = "rate", nullable = false)
    Double rate;

    // @NotBlank
    @Column(name = "comment", nullable = false)
    String comment;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "eval_status", nullable = false)
    EvalStatus evalStatus;

    public EvaluationForm(@JsonProperty("id") UUID id,
                @JsonProperty("author_id") Long authorId,
                @JsonProperty("rate") Double rate,
                @JsonProperty("comment") String comment,
                @JsonProperty("eval_status") EvalStatus evalStatus) {

        this.id = id;
        this.authorId = authorId;
        this.rate = rate;
        this.comment = comment;
        this.evalStatus = evalStatus;

        System.out.println(rate);
    }
}