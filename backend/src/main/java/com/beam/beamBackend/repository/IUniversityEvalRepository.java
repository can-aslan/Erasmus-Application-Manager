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
import com.beam.beamBackend.model.UniEvaluationForm;

@Transactional
@Repository
public interface IUniversityEvalRepository extends JpaRepository<UniEvaluationForm, UUID> {
    List<UniEvaluationForm> findByUniIdAndEvalStatus(UUID uniId, EvalStatus evalStatus);
    boolean existsByAuthorId(long authorId);
    public Optional<UniEvaluationForm> findEvalByAuthorId(long authorId);
    public Optional<UniEvaluationForm> findEvalByAuthorIdAndEvalStatus(long authorId, EvalStatus evalStatus);

    @Modifying
    @Query("update UniEvaluationForm form set form.comment = :comment, form.rate = :rate, form.evalStatus = :evalStatus where form.authorId = :authorId")
    int updateEval(@Param("comment") String comment, @Param("rate") Double rate, @Param("evalStatus") EvalStatus evalStat, @Param("authorId") Long authorId);
    
    @Query("SELECT AVG(eval.rate) FROM UniEvaluationForm eval")
    double getAverage();
}
