package com.beam.beamBackend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.enums.EvalStatus;
import com.beam.beamBackend.model.CourseEvaluationForm;
import com.beam.beamBackend.model.UniEvaluationForm;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.ICourseEvalRepository;
import com.beam.beamBackend.repository.IUniversityEvalRepository;
import com.beam.beamBackend.response.RCourseEval;
import com.beam.beamBackend.response.RUniEval;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EvaluationService implements IEvaluationService {
    private final IUniversityEvalRepository uniEvalRepo;
    private final ICourseEvalRepository courseEvalRepo;
    private final IAccountRepository userRepo;

    @Override
    public UUID evaluateUni(UniEvaluationForm uniEval) throws Exception {
        try {
            System.out.println(uniEval);
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(uniEval.getAuthorId())) {
                throw new Exception("user not found");
            }

            Optional<UniEvaluationForm> uniEvalDB = uniEvalRepo.findEvalByAuthorId(uniEval.getAuthorId());

            System.out.println(uniEval);
            System.out.println(uniEvalDB);
            if (!uniEvalDB.isPresent()) {
                uniEval.setId(UUID.randomUUID());
                uniEvalRepo.save(uniEval);
            } else if (uniEvalDB.get().getEvalStatus() == EvalStatus.SUBMITTED) {
                throw new Exception("uni eval already exists for this author");
            } else {
                uniEvalRepo.updateEval(uniEval.getComment(), uniEval.getRate(), uniEval.getEvalStatus(), uniEval.getAuthorId());
            }

            return uniEval.getUniId(); //change!!!
            
        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RUniEval getUniEval(UUID uniId) throws Exception {
        try {
            List<UniEvaluationForm> eval = uniEvalRepo.findByUniIdAndEvalStatus(uniId, EvalStatus.SUBMITTED);
            double avg = uniEvalRepo.getAverage();
            return new RUniEval(eval, avg);            
        } catch (Exception e) {
            System.out.println("cannot get eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public UUID evaluateCourse(CourseEvaluationForm courseEval) throws Exception {
        try {
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(courseEval.getAuthorId())) {
                throw new Exception("user not found");
            }

            Optional<CourseEvaluationForm> courseEvalDB = 
                        courseEvalRepo.findEvalByAuthorIdAndCourseId(courseEval.getAuthorId(), courseEval.getCourseId());

            if (!courseEvalDB.isPresent()) {
                courseEval.setId(UUID.randomUUID());
                courseEvalRepo.save(courseEval);
            } else if (courseEvalDB.get().getEvalStatus() == EvalStatus.SUBMITTED) {
                throw new Exception("course eval already exists for this author");
            } else {
                courseEvalRepo.updateEval(courseEval.getComment(), courseEval.getRate(), courseEval.getEvalStatus(), courseEval.getAuthorId());
            }

            // this will be inaccurate for update !!!!!
            return courseEval.getCourseId();
            
        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RCourseEval getCourseEval(UUID courseId) throws Exception {
        try {
            List<CourseEvaluationForm> eval = courseEvalRepo.findByCourseId(courseId);

            if (eval == null || eval.size() == 0) {
                throw new Exception("course not found or there is no eval"); //change maybe
            }

            return new RCourseEval(eval, courseEvalRepo.getAverage());            
        } catch (Exception e) {
            System.out.println("cannot get eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public UniEvaluationForm getStudentUniEval(Long authorId) throws Exception {
        try {
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(authorId)) {
                throw new Exception("user not found");
            }

            Optional<UniEvaluationForm> form = uniEvalRepo.findEvalByAuthorId(authorId);

            if (!form.isPresent()) {
                return new UniEvaluationForm(null, authorId, 0.0, "", EvalStatus.EMPTY, null);
            } 

            return form.get();            

        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public CourseEvaluationForm getStudentCourseEval(Long authorId, UUID courseId) throws Exception {
        try {
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(authorId)) {
                throw new Exception("user not found");
            }

            Optional<CourseEvaluationForm> form = courseEvalRepo.findEvalByAuthorIdAndCourseId(authorId, courseId);

            if (!form.isPresent()) {
                return new CourseEvaluationForm(null, authorId, 0.0, "", EvalStatus.EMPTY, courseId);
            }

            return form.get();

        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<CourseEvaluationForm> getStudentCourseEval(Long authorId) throws Exception {
        try {
            // throw exception if user is not found in the db
            if (!userRepo.existsByBilkentId(authorId)) {
                throw new Exception("user not found");
            }

            List<CourseEvaluationForm> form = courseEvalRepo.findEvalByAuthorId(authorId);

            return form;

        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }
}
