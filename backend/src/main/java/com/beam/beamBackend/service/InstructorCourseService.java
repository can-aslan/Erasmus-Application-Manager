package com.beam.beamBackend.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.enums.CourseRequestStatus;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.BilkentCourse;
import com.beam.beamBackend.model.CourseRequest;
import com.beam.beamBackend.model.EvaluationForm;
import com.beam.beamBackend.model.HostCourse;
import com.beam.beamBackend.model.InstructorCourse;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IBilkentCourseRepository;
import com.beam.beamBackend.repository.ICourseRequestRepository;
import com.beam.beamBackend.repository.IHostCourseRepository;
import com.beam.beamBackend.repository.IInstructorCourseRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import com.beam.beamBackend.request.InstructorCourseAdd;
import com.beam.beamBackend.request.InstructorCourseApproval;
import com.beam.beamBackend.response.RInstructorCourseAdd;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InstructorCourseService {
    private final IInstructorCourseRepository instructorCourseRepo;
    private final IBilkentCourseRepository bilkentCourseRepo;
    private final ICourseService courseService;
    private final IAccountRepository userRepo;
    // private final IStaffRepository staffRepository;
    private final ICourseRequestRepository courseRequestRepo;
    private final IUniversityRepository uniRepo;

    public RInstructorCourseAdd addCourseToInstructor(InstructorCourseAdd instructorCourses) throws Exception {
        try {
            // if the given instructor id is not valid throw an exception
            boolean instructorExist = userRepo.existsByIdAndUserType(instructorCourses.getInstructorId(), UserType.INSTRUCTOR);
    
            if (!instructorExist) {
                throw new Exception("instructor not found");
            }

            Set<String> coursesNotAdded = new HashSet<String>();
            Set<InstructorCourse> addedCourses = new HashSet<InstructorCourse>();

            for (String courseCode : instructorCourses.getBilkentCourseCode()) {
                // if the given bilkent course does not exist throw an error
                Optional<BilkentCourse> bilkentCourse = bilkentCourseRepo.findByCourseCode(courseCode);

                if (!bilkentCourse.isPresent()) {
                    throw new Exception("bilkent course not found");
                }                

                // if instructor already has the course throw an error
                boolean instructorAlreadyHasCourse = instructorCourseRepo.existsByInstructorIdAndBilkentCourseCode(instructorCourses.getInstructorId(), courseCode);

                if (instructorAlreadyHasCourse) {
                    coursesNotAdded.add(courseCode);
                    System.out.println("instructor already has the course");
                    // throw new Exception("instructor already has the course");
                    continue;
                }

                // if instructor course is unique add it to set
                addedCourses.add(new InstructorCourse(null, instructorCourses.getInstructorId(), courseCode));
            }            

            List<InstructorCourse> dbAddedCourses = instructorCourseRepo.saveAll(addedCourses);
            return new RInstructorCourseAdd(dbAddedCourses, coursesNotAdded);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }        
    }
    
    public List<BilkentCourse> getInstructorCourses(UUID instructorId) throws Exception {
        try {
            boolean instructorExist = userRepo.existsByIdAndUserType(instructorId, UserType.INSTRUCTOR);
    
            if (!instructorExist) {
                throw new Exception("instructor not found");
            }

            return instructorCourseRepo.findInstructorCourses(instructorId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<CourseRequest> getInstructorRequestedCourses(UUID instructorId) throws Exception {
        try {
            boolean instructorExist = userRepo.existsByIdAndUserType(instructorId, UserType.INSTRUCTOR);
    
            if (!instructorExist) {
                throw new Exception("instructor not found");
            }

            return instructorCourseRepo.findInstructorRequestedCourses(instructorId, CourseRequestStatus.PENDING);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public CourseRequest determineRequestedCourseStatus(InstructorCourseApproval requestResult) throws Exception {      

        try {
            // if status is not one of the provided enums throw an error
            boolean isValidStatus = EnumUtils.isValidEnum(CourseRequestStatus.class, requestResult.getCourseStatus().toString());

            if (!isValidStatus) {
                throw new Exception("not a valid status");
            }
    
            // if the course does not exist thow an exception
            Optional<CourseRequest> requestedCourse = courseRequestRepo.findByRequestId(requestResult.getCourseRequestId());
    
            if (!requestedCourse.isPresent()) {
                throw new Exception("requested course not found");
            }
    
            // if the given instructor does not exist throw an exception
            boolean instructorExist = userRepo.existsByIdAndUserType(requestResult.getInstructorId(), UserType.INSTRUCTOR);
    
            if (!instructorExist) {
                throw new Exception("instructor not found");
            }
    
            requestedCourse.get().setStatus(requestResult.getCourseStatus());
            requestedCourse.get().setFeedback(requestResult.getFeedback());

            CourseRequest request = requestedCourse.get();

            if (requestResult.getCourseStatus() == CourseRequestStatus.APPROVED) {
                University uni = uniRepo.findUniByName(request.getHostUniName());
                
                HostCourse hostCourse = new HostCourse(UUID.randomUUID(), request.getHostCode(), request.getName(), request.getHostEcts(),
                    request.getSyllabusLink(), request.getWebpage(), uni);

                System.out.println("host course: " + hostCourse);
                courseService.addHostCourse(hostCourse);
            }

            return courseRequestRepo.save(request);
        } catch (Exception e) {
            e.getStackTrace();
            throw e;
        }
    }
}
