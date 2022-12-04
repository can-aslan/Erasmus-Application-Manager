package com.beam.beamBackend.model;

import java.util.HashMap;

public class CourseWishlist {

    // Properties
    HashMap<BilkentCourse, OtherUniCourse> selectedCourses; // What does "set" mean on the diagram?
    boolean isApproved;
    long studentId;

    // Constructors
    public CourseWishlist(HashMap<BilkentCourse, OtherUniCourse> selectedCourses ){
        this.selectedCourses = selectedCourses;
    }

    public CourseWishlist(HashMap<BilkentCourse,OtherUniCourse> selectedCourses, boolean isApproved, long studentId) {
        this.selectedCourses = selectedCourses;
        this.isApproved = isApproved;
        this.studentId = studentId;
    }

    // Methods


    public HashMap<BilkentCourse,OtherUniCourse> getSelectedCourses() {
        return this.selectedCourses;
    }

    public void setSelectedCourses(HashMap<BilkentCourse,OtherUniCourse> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }

    public boolean isIsApproved() {
        return this.isApproved;
    }

    public boolean getIsApproved() {
        return this.isApproved;
    }

    public void setIsApproved(boolean isApproved) {
        this.isApproved = isApproved;
    }

    public long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }


}
