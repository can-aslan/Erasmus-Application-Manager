package com.beam.beamBackend.request;

import java.util.UUID;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;
import com.beam.beamBackend.enums.Semester;
import com.beam.beamBackend.enums.Sex;
import com.beam.beamBackend.enums.StudyType;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {//maybe add not null vs
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
    private UUID homeUniId;
    private UUID hostUniId;
    private String academicYear;
    private Semester semester;
    private UUID coordinatorId;

    // @Deprecated
    // public Student toStudent(StudentRequest sReq, User u, University homeUni, University hostUni) {
    //     return new Student(null, u, sReq.getDepartment1(), sReq.getFaculty1(),
    //                         sReq.getDepartment2(), sReq.getFaculty2(), sReq.getTelephoneNo(),
    //                         sReq.getStudyType(), sReq.getNationality(), sReq.getDateOfBirth(),
    //                         sReq.getSex(), homeUni, hostUni, sReq.getAcademicYear(), sReq.getSemester());
    // }
}
