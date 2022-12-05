package com.beam.beamBackend.model;

import com.beam.beamBackend.enums.CourseApproval;
import com.beam.beamBackend.enums.*;

public class OtherUniCourse extends Course{
    // Properties
    String courseCode;
    String coursename;
    Department department;
    long id;
    double ects;
    String syllabus; 
    long uniId;
    CourseApproval courseApproval;
    

    // Constructors

    public OtherUniCourse(String courseCode, String coursename, Department department, long id, double ects, String syllabus, long uniId, CourseApproval courseApproval) {
        this.courseCode = courseCode;
        this.coursename = coursename;
        this.department = department;
        this.id = id;
        this.ects = ects;
        this.syllabus = syllabus;
        this.uniId = uniId;
        this.courseApproval = courseApproval;
    }


    // Methods

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCoursename() {
        return this.coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getEcts() {
        return this.ects;
    }

    public void setEcts(double ects) {
        this.ects = ects;
    }

    public String getSyllabus() {
        return this.syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public long getUniId() {
        return this.uniId;
    }

    public void setUniId(long uniId) {
        this.uniId = uniId;
    }

    public CourseApproval getCourseApproval() {
        return this.courseApproval;
    }

    public void setCourseApproval(CourseApproval courseApproval) {
        this.courseApproval = courseApproval;
    }



}
