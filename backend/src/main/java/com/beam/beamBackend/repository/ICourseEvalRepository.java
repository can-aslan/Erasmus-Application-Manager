package com.beam.beamBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.model.CourseEvaluationForm;

@Transactional
@Repository
public interface ICourseEvalRepository extends JpaRepository<CourseEvaluationForm, UUID> {
    boolean existsByAuthorId(long authorId);
    public Optional<CourseEvaluationForm> findEvalByAuthorId(long authorId);
    public List<CourseEvaluationForm> findByCourseId(UUID courseId);
    public Optional<CourseEvaluationForm> findEvalByAuthorIdAndEvalStatus(long authorId, EvalStatus evalStatus);

    @Modifying
    @Query("update CourseEvaluationForm form set form.comment = :comment, form.rate = :rate, form.evalStatus = :eval_status where form.authorId = :author_id")
    int updateEval(@Param("comment") String comment, @Param("rate") Double rate, @Param("eval_status") EvalStatus evalStat, @Param("author_id") Long authorId);

    @Query("SELECT AVG(eval.rate) FROM CourseEvaluationForm eval")
    double getAverage();
}