package com.beam.beamBackend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.HostCourse;
import com.beam.beamBackend.repository.IBilkentCourseRepository;
import com.beam.beamBackend.repository.IHostCourseRepository;
import com.beam.beamBackend.repository.IUniversityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final IBilkentCourseRepository bilkentCourseRepo;
    private final IHostCourseRepository hostCourseRepo;
    private final IUniversityRepository uniRepo;

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

    public HostCourse addHostCourse(HostCourse hostCourse) throws Exception {        

        try {
            boolean courseExists = hostCourseRepo.existsByCourseCode(hostCourse.getCourseCode());

            // if course is already saved don't save it again
            if (courseExists) {
                throw new Exception("course already exists");
            }

            boolean universityExsits = uniRepo.existsById(hostCourse.getUniversityId());
            System.out.println("is uniid valid: " + universityExsits);

            // if university does not exist don't save course
            if (!universityExsits) {
                throw new Exception("not a valid department");
            }

            return hostCourseRepo.save(hostCourse);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public HostCourse getHostCourseById(UUID courseId) throws Exception {        

        try {
            Optional<HostCourse> hostCourse = hostCourseRepo.findByCourseId(courseId);

            if (!hostCourse.isPresent()) {
                throw new Exception("course not found");
            }

            return hostCourse.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<HostCourse> getHostCourseByUniId(UUID uniId) throws Exception {        

        try {
            List<HostCourse> hostCourse = hostCourseRepo.findByUniversityId(uniId);

            return hostCourse;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<BilkentCourse> getBilkentCourseByDepartment(Department department) throws Exception {        

        try {
            List<BilkentCourse> bilkentCourse = bilkentCourseRepo.findByDepartment(department);

            return bilkentCourse;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }    
    }

    public List<BilkentCourse> getAllBilkentCourse() throws Exception {    
        try {
            return bilkentCourseRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<HostCourse> getAllHostCourse() throws Exception {    
        try {
            return hostCourseRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
