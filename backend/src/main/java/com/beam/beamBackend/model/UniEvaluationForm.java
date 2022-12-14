package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.EvalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "uni_eval")
@NoArgsConstructor
@AllArgsConstructor
public class UniEvaluationForm {

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
    
    @NotNull
    @Column(name = "uni_id", nullable = false)
    private UUID uniId;

    public UniEvaluationForm(@JsonProperty("id") UUID id,
                @JsonProperty("author_id") Long authorId,
                @JsonProperty("rate") Double rate,
                @JsonProperty("comment") String comment,
                @JsonProperty("uni_id") UUID uniId,
                @JsonProperty("eval_status") EvalStatus evalStatus) {  
        this.uniId = uniId;
        this.id = id;
        this.authorId = authorId;
        this.rate = rate;
        this.comment = comment;
        this.evalStatus = evalStatus;
    }    
}
