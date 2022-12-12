package com.beam.beamBackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.model.UniEvaluationForm;

@Qualifier("uni_evaluation")
@Repository
public class UniEvaluationRepository {
    private static List<UniEvaluationForm> DB = new ArrayList<>();

    public Boolean save(UniEvaluationForm uniEval) {
        // System.out.println("tset we are in repo");
        
        System.out.println("user id:" + uniEval.getUniId());
        System.out.println("user id:" + uniEval);
        return DB.add(uniEval); 
    }

    public List<UniEvaluationForm> findAll() {
        return DB;
    }

    public List<UniEvaluationForm> findByUniIdAndEvalStatus(UUID uniId, EvalStatus evalStatus) {
        List<UniEvaluationForm> evals = DB.stream()
                    .filter(uni -> uni.getUniId().equals(uniId) && uni.getEvalStatus() == evalStatus)
                    .toList();
        System.out.println("hello in: " + uniId);

        return evals;
    }

    public EvalStatus findEvalStatusByAuthorId(long authorId) {
        EvalStatus uniEval = DB.stream()
                    .filter(uni -> uni.getAuthorId() == authorId)
                    .findFirst().get().getEvalStatus();
        
        return uniEval;
    }

    public boolean existsByAuthorId(long authorId) {
        return DB.stream()
                    .filter(uniEval -> uniEval.getAuthorId() == authorId)
                    .findFirst().isPresent();
    }

}
