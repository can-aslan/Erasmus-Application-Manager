package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.UniEvaluationForm;
import com.beam.beamBackend.response.RCourseEval;
import com.beam.beamBackend.response.RUniEval;

public interface IEvaluationService {
    UUID evaluateUni(UniEvaluationForm uniEval) throws Exception;
    RUniEval getUniEval(UUID uniId) throws Exception;
    UUID evaluateCourse(CourseEvaluationForm courseEval) throws Exception;
    RCourseEval getCourseEval(UUID courseId) throws Exception;
    UniEvaluationForm getStudentUniEval(Long authorId) throws Exception;
    CourseEvaluationForm getStudentCourseEval(Long authorId, UUID courseId) throws Exception;
    List<CourseEvaluationForm> getStudentCourseEval(Long authorId) throws Exception;
}
