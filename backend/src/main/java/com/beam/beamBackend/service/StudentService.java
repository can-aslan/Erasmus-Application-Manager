package com.beam.beamBackend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.model.HostCourse;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IHostCourseRepository;
import com.beam.beamBackend.repository.IStaffRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import com.beam.beamBackend.request.StudentRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService implements IStudentService {
    private final IStudentRepository studentRepo;
    private final IAccountRepository userRepo;
    private final IUniversityRepository uniRepository;
    private final IStaffRepository staffRepository;
    private final IHostCourseRepository hostCourseRepo;

    @Override
    public UUID addStudent(StudentRequest student) throws Exception {
        try {
            // if student already exists throw an exception
            Optional<User> user = userRepo.findUserById(student.getUserId());

            if (!user.isPresent()) {
                throw new Exception("user not found");
            }

            // if universities don't exist throw an exception
            Optional<University> homeUni = uniRepository.findUniById(student.getHomeUniId());

            if (!homeUni.isPresent()) {
                throw new Exception("home university not found");
            }
            
            Optional<University> hostUni = uniRepository.findUniById(student.getHomeUniId());

            if (!hostUni.isPresent()) {
                throw new Exception("host university not found");
            }
            
            // if coordinator does not exists throw ane exception
            Optional<Staff> coordinator = staffRepository.findByUserId(student.getCoordinatorId());

            if (!coordinator.isPresent()) {
                throw new Exception("coordinator not found");
            }

            Student studentDB = Student.toStudent(student, user.get(), homeUni.get(), hostUni.get(), coordinator.get());
            studentDB.setId(UUID.randomUUID());
            System.out.println("helodb: " + studentDB);

            studentRepo.save(studentDB);
            return studentDB.getUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }        
    }

    @Override
    public List<HostCourse> getHostCoursesOfStudentHostUni(Long bilkentId) throws Exception {
        try {
            Student student = getStudentByBilkentId(bilkentId);
            UUID hostUniId = student.getHostUni().getId();

            return hostCourseRepo.findByUniversityId(hostUniId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    // this is user id
    @Override
    public Student getStudentById(UUID id) throws Exception {
        try {
            Optional<Student> s = studentRepo.findByUserId(id);

            if (!s.isPresent()) {
                throw new Exception("user not found");
            }

            return s.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Student> getAll() throws Exception {
        try {
            return studentRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public University getUniOfStudent(Long bilkentId) throws Exception {
        Optional<Student> student = studentRepo.findByUserBilkentId(bilkentId);

        if (!student.isPresent()) {
            throw new Exception("user not found");
        }

        return student.get().getHostUni();
    }

    @Override
    public UUID getUniIdOfStudent(Long bilkentId) throws Exception {
        Optional<Student> student = studentRepo.findByUserBilkentId(bilkentId);

        if (!student.isPresent()) {
            throw new Exception("user not found");
        }

        return student.get().getHostUni().getId();
    }

    @Override
    public Student getStudentByBilkentId(Long bilkentId) throws Exception {
        Optional<Student> student = studentRepo.findByUserBilkentId(bilkentId);

        if (!student.isPresent()) {
            throw new Exception("user not found");
        }

        return student.get();
    }

    @Override
    public Student updateStudent(StudentRequest student) throws Exception {
        try {
            Student studentToUpdate = studentRepo.getReferenceById(student.getUserId());

            if (student.getDepartment1() != null) {
                studentToUpdate.setDepartment(student.getDepartment1());
            }

            if (student.getDepartment2() != null) {
                studentToUpdate.setDepartment2(student.getDepartment2());
            }

            if (student.getFaculty1() != null) {
                studentToUpdate.setFaculty(student.getFaculty1());
            }

            if (student.getFaculty2() != null) {
                studentToUpdate.setFaculty2(student.getFaculty2());
            }

            if (student.getTelephoneNo() != null) {
                studentToUpdate.setTelephoneNo(student.getTelephoneNo());
            }

            if (student.getStudyType() != null) {
                studentToUpdate.setStudyType(student.getStudyType());
            }

            if (student.getNationality() != null) {
                studentToUpdate.setNationality(student.getNationality());
            }

            if (student.getDateOfBirth() != null) {
                studentToUpdate.setDateOfBirth(student.getDateOfBirth());
            }

            if (student.getSex() != null) {
                studentToUpdate.setSex(student.getSex());
            }

            if (student.getAcademicYear() != null) {
                studentToUpdate.setAcademicYear(student.getAcademicYear());
            }

            if (student.getSemester() != null) {
                studentToUpdate.setSemester(student.getSemester());
            }

            if (student.getHomeUniId() != null) {
                Optional<University> homeUniNew = uniRepository.findUniById(student.getHomeUniId());

                if (!homeUniNew.isPresent()) {
                    throw new Exception("uni not found");
                }

                studentToUpdate.setHomeUni(homeUniNew.get());
            }

            if (student.getHostUniId() != null) {
                Optional<University> hostUniNew = uniRepository.findUniById(student.getHostUniId());

                if (!hostUniNew.isPresent()) {
                    throw new Exception("uni not found");
                }

                studentToUpdate.setHomeUni(hostUniNew.get());
            }

            return studentRepo.save(studentToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
