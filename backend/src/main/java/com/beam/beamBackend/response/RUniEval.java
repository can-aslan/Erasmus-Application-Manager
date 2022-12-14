package com.beam.beamBackend.response;

import java.util.List;

import com.beam.beamBackend.model.UniEvaluationForm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RUniEval {
    private List<UniEvaluationForm> evalList;
    private double avg;
}
