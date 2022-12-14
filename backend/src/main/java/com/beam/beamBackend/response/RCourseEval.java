package com.beam.beamBackend.response;

import java.util.List;

import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.UniEvaluationForm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RCourseEval {
    private List<CourseEvaluationForm> evalList;
    private double avg;
}
