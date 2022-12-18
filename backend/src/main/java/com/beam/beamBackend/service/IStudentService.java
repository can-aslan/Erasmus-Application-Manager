package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.request.StudentRequest;
import com.beam.beamBackend.model.HostCourse;

public interface IStudentService {
    UUID addStudent(StudentRequest student) throws Exception;
    List<HostCourse> getHostCoursesOfStudentHostUni(Long bilkentId) throws Exception;
    Student getStudentById(UUID id) throws Exception;
    List<Student> getAll() throws Exception;
    University getUniOfStudent(Long bilkentId) throws Exception;
    UUID getUniIdOfStudent(Long bilkentId) throws Exception;
    Student getStudentByBilkentId(Long bilkentId) throws Exception;
    Student updateStudent(StudentRequest student) throws Exception;
}
