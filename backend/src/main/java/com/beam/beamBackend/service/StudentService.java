package com.beam.beamBackend.service;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import com.beam.beamBackend.request.ChangePassword;
import com.beam.beamBackend.request.StudentRequest;
import com.beam.beamBackend.response.RLoginUser;
import com.beam.beamBackend.response.RRefreshToken;
import com.beam.beamBackend.response.RUserList;
import com.beam.beamBackend.response.ResponseId;
import com.beam.beamBackend.security.JWTFilter;
import com.beam.beamBackend.security.JWTUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StudentService {
    final private IStudentRepository studentRepo;
    final private IAccountRepository userRepo;
    private final IUniversityRepository uniRepository;

    public UUID addStudent(StudentRequest student) throws Exception {
        try {
            Optional<User> user = userRepo.findUserById(student.getUserId());

            if (!user.isPresent()) {
                throw new Exception("user not found");
            }

            Optional<University> homeUni = uniRepository.findUniById(student.getHomeUniId());

            if (!homeUni.isPresent()) {
                throw new Exception("home university not found");
            }
            
            Optional<University> hostUni = uniRepository.findUniById(student.getHomeUniId());

            if (!hostUni.isPresent()) {
                throw new Exception("host university not found");
            }

            Student studentDB = Student.toStudent(student, user.get(), homeUni.get(), hostUni.get());
            // studentDB.setId(UUID.randomUUID());
            System.out.println("helodb: " + studentDB);

            studentRepo.save(studentDB);
            return studentDB.getUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }

    // public Student getStudentByBilkentId(Long bilkentId) throws Exception {
    //     try {
    //         Optional<Student> s = studentRepo.findByBilkentId(bilkentId);

    //         if (!s.isPresent()) {
    //             throw new Exception("user not found");
    //         }

    //         return s.get();
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         throw e;
    //     }
    // }

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

    public List<Student> getAll() throws Exception {
        try {
            return studentRepo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public University getUniOfStudent(Long bilkentId) throws Exception {
        Optional<Student> student = studentRepo.findByUserBilkentId(bilkentId);

        if (!student.isPresent()) {
            throw new Exception("user not found");
        }

        return student.get().getHostUni();
    }

    public UUID getUniIdOfStudent(Long bilkentId) throws Exception {
        Optional<Student> student = studentRepo.findByUserBilkentId(bilkentId);

        if (!student.isPresent()) {
            throw new Exception("user not found");
        }

        return student.get().getHostUni().getId();
    }

    public Student getStudentByBilkentId(Long bilkentId) throws Exception {
        Optional<Student> student = studentRepo.findByUserBilkentId(bilkentId);

        if (!student.isPresent()) {
            throw new Exception("user not found");
        }

        return student.get();
    }

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
