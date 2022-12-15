package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.EvalStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
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

@Entity
@Data
@Table(name = "course_eval")
@NoArgsConstructor
@AllArgsConstructor
public class CourseEvaluationForm{
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    UUID id; //evalId

    @NotNull
    @Column(name = "author_id", nullable = false)
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
    @Column(name = "course_id", nullable = false)
    private UUID courseId; //courseId

    public CourseEvaluationForm(@JsonProperty("id") UUID id,
                @JsonProperty("author_id") long authorId,
                @JsonProperty("rate") double rate,
                @JsonProperty("comment") String comment,
                @JsonProperty("course_id") UUID courseId,
                @JsonProperty("eval_status") EvalStatus evalStatus) {  
        this.courseId = courseId;
        this.id = id;
        this.authorId = authorId;
        this.rate = rate;
        this.comment = comment;
        this.evalStatus = evalStatus;
    }    
}
