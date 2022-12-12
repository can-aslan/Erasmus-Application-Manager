package com.beam.beamBackend.repository;

import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.model.Course;

public interface ICourseRepository {
    /**/
    boolean saveCourse(Course course);

    boolean editCourseByCourseUUID(UUID courseUUID, Course newCourse);
    boolean editCourseByUniversityIDAndCourseID(Long universityID, Long courseID, Course newCourse);
    
    boolean deleteCourseByCourseUUID(UUID courseUUID);
    boolean deleteCourseByUniversityIDAndCourseID(Long universityID, Long courseID);
    
    boolean courseExistsByCourseUUID(UUID courseUUID);
    boolean courseExistsByUniversityIDAndCourseID(Long universityID, Long courseID);

    List<Course> getAllCourses();
    List<Course> getAllCoursesByUniversityID(Long universityID);
    
}
