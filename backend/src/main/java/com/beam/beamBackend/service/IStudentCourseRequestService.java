package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.UUID;
import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.File;

public interface IStudentCourseRequestService {
    boolean requestCourse(
        UUID studentId,
        String code,
        String name,
        String bilkentCourse,
        String webpage
    );

    boolean requestCourse(
        UUID studentId,
        String code,
        String name,
        String bilkentCourse,
        String webpage,
        File additionalInfo
    );

    ArrayList<Course> getPreviouslyRequestedCourses();
}
