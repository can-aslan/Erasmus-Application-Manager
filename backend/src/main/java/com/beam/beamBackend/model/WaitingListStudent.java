package com.beam.beamBackend.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class WaitingListStudent {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student", nullable = false)
    private Student student;

    @Column(name = "waiting_index", nullable = false)
    private int waitingListIndex;
}
