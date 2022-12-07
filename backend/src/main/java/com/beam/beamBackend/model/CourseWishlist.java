package com.beam.beamBackend.model;

import java.util.HashMap;
import javax.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class CourseWishlist {

    HashMap<BilkentCourse, OtherUniCourse> selectedCourses; // What does "set" mean on the diagram?
    boolean isApproved;
    long studentId;

    public CourseWishlist(HashMap<BilkentCourse, OtherUniCourse> selectedCourses ){
        this.selectedCourses = selectedCourses;
    }

    public CourseWishlist(HashMap<BilkentCourse,OtherUniCourse> selectedCourses, boolean isApproved, long studentId) {
        this.selectedCourses = selectedCourses;
        this.isApproved = isApproved;
        this.studentId = studentId;
    }
}
