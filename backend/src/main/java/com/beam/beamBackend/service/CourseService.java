package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.HostCourse;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.repository.IBilkentCourseRepository;
import com.beam.beamBackend.repository.IHostCourseRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import com.beam.beamBackend.request.ApprovedCourse;
import com.beam.beamBackend.request.HostCourseRequestBody;
import com.beam.beamBackend.response.RHostCourse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final IBilkentCourseRepository bilkentCourseRepo;
    private final IHostCourseRepository hostCourseRepo;
    private final IUniversityRepository uniRepo;

    @Override
    public BilkentCourse addBilkentCourse(BilkentCourse bilkentCourse) throws Exception {        

        try {
            boolean courseExists = bilkentCourseRepo.existsByCourseCode(bilkentCourse.getCourseCode());

            if (courseExists) {
                throw new Exception("course already exists");
            }

            boolean isValidDepartment = EnumUtils.isValidEnum(Department.class, bilkentCourse.getDepartment().toString());
            System.out.println("is dept valid: " + isValidDepartment);

            if (!isValidDepartment) {
                throw new Exception("not a valid department");
            }

            return bilkentCourseRepo.save(bilkentCourse);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public BilkentCourse getBilkentCourseById(UUID courseId) throws Exception {        

        try {
            Optional<BilkentCourse> bilkentCourse = bilkentCourseRepo.findByCourseId(courseId);

            if (!bilkentCourse.isPresent()) {
                throw new Exception("course not found");
            }

            return bilkentCourse.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RHostCourse addHostCourse(HostCourseRequestBody hostCourse) throws Exception {        

        try {
            Optional<University> university = uniRepo.findById(hostCourse.getUniversityId());
            System.out.println("is university valid: " + university);

            // if university does not exist don't save course
            if (!university.isPresent()) {
                throw new Exception("not a valid university");
            }

            boolean courseExists = hostCourseRepo.existsByCourseCodeAndUniversityId(hostCourse.getCourseCode(), hostCourse.getUniversityId());

            // if course is already saved don't save it again
            if (courseExists) {
                throw new Exception("course already exists");
            }            

            HostCourse hostCourseDb = HostCourse.toHostCourse(hostCourse, university.get());
            hostCourseDb = hostCourseRepo.save(hostCourseDb);
            return new RHostCourse(hostCourseDb);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RHostCourse getHostCourseById(UUID courseId) throws Exception {        

        try {
            Optional<HostCourse> hostCourse = hostCourseRepo.findByCourseId(courseId);

            if (!hostCourse.isPresent()) {
                throw new Exception("course not found");
            }

            return new RHostCourse(hostCourse.get());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<RHostCourse> getHostCourseByUniId(UUID uniId) throws Exception {

        try {
            List<HostCourse> hostCourse = hostCourseRepo.findByUniversityId(uniId);
            List<RHostCourse> responseList = new ArrayList<RHostCourse>();

            for (HostCourse h : hostCourse) {
                responseList.add(new RHostCourse(h));
            }

            return responseList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public List<BilkentCourse> getBilkentCourseByDepartment(Department department) throws Exception {        

        try {
            List<BilkentCourse> bilkentCourse = bilkentCourseRepo.findByDepartment(department);

            return bilkentCourse;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }    
    }

    @Override
    public List<BilkentCourse> getAllBilkentCourse() throws Exception {    
        try {
            return bilkentCourseRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    @Override
    public List<RHostCourse> getAllHostCourse() throws Exception {    
        try {
            List<HostCourse> hostCourse = hostCourseRepo.findAll();
            List<RHostCourse> responseList = new ArrayList<RHostCourse>();

            for (HostCourse h : hostCourse) {
                responseList.add(new RHostCourse(h));
            }

            return responseList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public RHostCourse addHostCourse(HostCourse hostCourse) throws Exception {
        try {
            Optional<University> university = uniRepo.findById(hostCourse.getUniversity().getId());
            System.out.println("is university valid: " + university);

            // if university does not exist don't save course
            if (!university.isPresent()) {
                throw new Exception("not a valid university");
            }

            boolean courseExists = hostCourseRepo.existsByCourseCodeAndUniversityId(hostCourse.getCourseCode(), hostCourse.getUniversity().getId());

            // if course is already saved don't save it again
            if (courseExists) {
                throw new Exception("course already exists");
            }            

            HostCourse hostCourseDb = hostCourseRepo.save(hostCourse);
            return new RHostCourse(hostCourseDb);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // @Override
    // public BilkentCourse addApprovedCourse(ApprovedCourse approvedCourse) throws Exception {

    //     try {
    //         Optional<BilkentCourse> bilkentCourse =  bilkentCourseRepo.findByCourseId(approvedCourse.getBilkentCourseId());

    //         if (!bilkentCourse.isPresent()) {
    //             throw new Exception("course not found");
    //         }

    //         List<HostCourse> hostCourses =  hostCourseRepo.findByCourseIdIn(approvedCourse.getHostCourseId());

    //         bilkentCourse.get().getApprovedCourses().addAll(hostCourses);

    //         return bilkentCourseRepo.save(bilkentCourse.get());
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
        
    // }

    // //not working at the moment :((((((
    // @Override
    // public List<Object> getAllApprovedCoursesInUni(UUID hostUniId) throws Exception {
    //     try {
    //         Optional<University> hostUni = uniRepo.findById(hostUniId);

    //         if (!hostUni.isPresent()) {
    //             throw new Exception("university not found");
    //         }

    //         System.out.println(((BilkentCourse) bilkentCourseRepo.findApprovedByUniId(hostUniId).get(0)).getApprovedCourses());

    //         return bilkentCourseRepo.findApprovedByUniId(hostUniId);
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
        
    // }
}
