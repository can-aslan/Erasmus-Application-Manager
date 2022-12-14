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
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IStudentRepository;
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

    public UUID addStudent(StudentRequest student) throws Exception {
        try {
            Optional<User> user = userRepo.findUserById(student.getUserId());

            if (!user.isPresent()) {
                throw new Exception("user not found");
            }

            System.out.println("helo: " + student);
            Student studentDB = student.toStudent(student, user.get());
            studentDB.setId(UUID.randomUUID());
            System.out.println("helodb: " + studentDB);

            studentRepo.save(studentDB);
            return studentDB.getId();
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
            Optional<Student> s = studentRepo.findById(id);

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

    public Student updateStudent(StudentRequest student) {
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

            return studentRepo.save(studentToUpdate);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        
    }
}
