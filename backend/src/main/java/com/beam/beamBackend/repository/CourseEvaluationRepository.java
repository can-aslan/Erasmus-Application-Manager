package com.beam.beamBackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.CourseEvaluationForm;


@Deprecated
@Qualifier("course_evaluation")
@Repository
public class CourseEvaluationRepository {
    private static List<CourseEvaluationForm> DB = new ArrayList<>();

    public Boolean save(CourseEvaluationForm courseEval) {
        // System.out.println("tset we are in repo");
        
        System.out.println("user id:" + courseEval.getCourseId());
        System.out.println("user id:" + courseEval);
        return DB.add(courseEval); 
    }

    public List<CourseEvaluationForm> findAll() {
        return DB;
    }

    public List<CourseEvaluationForm> findByCourseId(UUID courseId) {
        Optional<CourseEvaluationForm> form = DB.stream()
                    .filter(uni -> uni.getCourseId() == courseId)
                    .findFirst();
                    System.out.println(form);
        System.out.println("hello in: " + courseId);
        return DB.stream()
                    .filter(uni -> uni.getCourseId().equals(courseId))
                    .toList();
    }

    public List<CourseEvaluationForm> findByUniId(UUID uniId) {
        return null;
        /*
            SELECT eval.id, eval.rate, eval.comment, eval.courseId
            FROM CourseEvaluation eval
            INNER JOIN Course c
            ON eval.courseId = c.courseId
            WHERE c.uniId = uniId
         */
    }

    public boolean existsByAuthorId(long authorId) {
        return DB.stream()
                    .filter(courseEval -> courseEval.getAuthorId() == authorId)
                    .findFirst().isPresent();
    }
}
