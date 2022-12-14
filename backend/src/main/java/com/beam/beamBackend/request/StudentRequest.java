package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;
import com.beam.beamBackend.enums.Sex;
import com.beam.beamBackend.enums.StudyType;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private UUID userId;
    private Department department1;
    private Faculty faculty1;
    private Department department2;
    private Faculty faculty2;
    private String telephoneNo;
    private StudyType studyType;
    private String nationality;
    private String dateOfBirth;
    private Sex sex;

    public Student toStudent(StudentRequest sReq, User u) {
        return new Student(null, u, department1, faculty1, department2, faculty2, telephoneNo, studyType, nationality, dateOfBirth, sex);
    }
}
