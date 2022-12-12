package com.beam.beamBackend.model;

import java.util.UUID;

import javax.persistence.Entity;

import com.beam.beamBackend.enums.EvalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
public class CourseEvaluationForm extends EvaluationForm {
    // @Id
    // private UUID id; //evalId

    // @NotNull
    // private long authorId;

    // @NotNull
    // private double rate;

    // @NotBlank
    // private String comment;

    @NotNull
    private UUID courseId; //courseId

    public CourseEvaluationForm(@JsonProperty("id") UUID id,
                @JsonProperty("authorId") long authorId,
                @JsonProperty("rate") double rate,
                @JsonProperty("comment") String comment,
                @JsonProperty("courseId") UUID courseId,
                @JsonProperty("evalStatus") EvalStatus evalStatus) {
        super(id, authorId, rate, comment, evalStatus);   
        this.courseId = courseId;
    }
    
}
