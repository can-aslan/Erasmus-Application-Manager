package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import com.beam.beamBackend.request.StudentRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "department1", nullable = false)
    private Department department;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "faculty1", nullable = false)
    private Faculty faculty;
    
    // these are for minor or major students
    @Enumerated(EnumType.STRING)
    @Column(name = "department2", nullable = true)
    private Department department2;

    @Enumerated(EnumType.STRING)
    @Column(name = "faculty2", nullable = true)
    private Faculty faculty2;

    @NotBlank
    @Size(min = 8)
    @Column(name = "telephone", nullable = false)
    private String telephoneNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "study_type", nullable = false)
    private StudyType studyType;

    @NotBlank
    @Column(name = "nationality", nullable = false)
    private String nationality;

    @NotBlank
    @Column(name = "dateOfBirth", nullable = false)
    private String dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sex", nullable = false)
    private Sex sex;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_uni_id", referencedColumnName = "id", nullable = true)
    private University homeUni;
    
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_uni_id", referencedColumnName = "id", nullable = true)
    private University hostUni;

    // @NotNull
    @Column(name = "academic_year", nullable = true)
    private String academicYear;
    
    // @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = true)
    private Semester semester;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coordinator", referencedColumnName = "id", nullable = true)
    private Staff coordinator;

    public static Student toStudent(StudentRequest sReq, User u, University homeUni, University hostUni, Staff coordinator) {
        return new Student(null, u, sReq.getDepartment1(), sReq.getFaculty1(),
                            sReq.getDepartment2(), sReq.getFaculty2(), sReq.getTelephoneNo(),
                            sReq.getStudyType(), sReq.getNationality(), sReq.getDateOfBirth(),
                            sReq.getSex(), homeUni, hostUni, sReq.getAcademicYear(), sReq.getSemester(), coordinator);
    }
}