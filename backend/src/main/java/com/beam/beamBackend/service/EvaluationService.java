package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.EvaluationForm;
import com.beam.beamBackend.model.UniEvaluationForm;
import com.beam.beamBackend.repository.AccountRepository;
import com.beam.beamBackend.repository.CourseEvaluationRepository;
import com.beam.beamBackend.repository.UniEvaluationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EvaluationService {

    private final UniEvaluationRepository uniEvalRepo;
    private final CourseEvaluationRepository courseEvalRepo;
    private final AccountRepository userRepo;

    public UUID evaluateUni(UniEvaluationForm uniEval) throws Exception {
        try {
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(uniEval.getAuthorId())) {
                throw new Exception("user not found");
            }

            EvalStatus evalStatus = uniEvalRepo.findEvalStatusByAuthorId(uniEval.getAuthorId());

            if (evalStatus == null) {
                uniEval.setId(UUID.randomUUID());
                Boolean result = uniEvalRepo.save(uniEval);
            } else if (evalStatus == EvalStatus.SUBMITTED) {
                throw new Exception("uni eval already exists for this author");
            } else {
                // update current evaluation
            }

            // this will be inaccurate for update !!!!!
            return uniEval.getUniId();
            
        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    public List<UniEvaluationForm> getUniEval(UUID uniId) throws Exception {
        try {
            List<UniEvaluationForm> result = uniEvalRepo.findByUniIdAndEvalStatus(uniId, EvalStatus.SUBMITTED);
            return result;            
        } catch (Exception e) {
            System.out.println("cannot get eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    public UUID evaluateCourse(CourseEvaluationForm courseEval) throws Exception {
        try {
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(courseEval.getAuthorId())) {
                throw new Exception("user not found");
            }

            EvalStatus evalStatus = null;
            // courseEvalRepo.findEvalStatusByAuthorId(courseEval.getAuthorId());

            if (evalStatus == null) {
                courseEval.setId(UUID.randomUUID());
                Boolean result = courseEvalRepo.save(courseEval);
            } else if (evalStatus == EvalStatus.SAVED) {
                throw new Exception("uni eval already exists for this author");
            } else {
                // update current evaluation
            }

            // this will be inaccurate for update !!!!!
            return courseEval.getCourseId();
            
        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    public List<CourseEvaluationForm> getCourseEval(UUID courseId) throws Exception {
        try {
            List<CourseEvaluationForm> result = courseEvalRepo.findByCourseId(courseId);

            if (result == null) {
                throw new Exception("course not found or there is no eval"); //cahnge maybe
            }
            return result;            
        } catch (Exception e) {
            System.out.println("cannot get eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    public List<CourseEvaluationForm> getAllCourseEval(UUID uniId) throws Exception {
        try {

            // check if uni id exists

            //
            List<CourseEvaluationForm> result = courseEvalRepo.findByUniId(uniId);

            if (result == null) {
                throw new Exception("no eval is found"); //maybe not error?
            }
            return result;            
        } catch (Exception e) {
            System.out.println("cannot get eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    public EvaluationForm getSavedUniEval(long authorId) throws Exception {
        try {
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(authorId)) {
                throw new Exception("user not found");
            }

            EvalStatus evalStatus = uniEvalRepo.findEvalStatusByAuthorId(authorId);

            if (evalStatus == null) {
                // empty evaluation
            } else if (evalStatus == EvalStatus.SUBMITTED) {
                throw new Exception("uni eval already exists for this author");
            } else {
                // get saved eval
            }

            // this will be inaccurate for update !!!!!
            return null;//here
            
        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    // public EvaluationForm getSavedCourseEval(long authorId, UUID courseId) {

    // }

    
}
