package com.beam.beamBackend.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.Course;

@Qualifier("course-arrayList")
@Repository
public class CourseRepositoryArrayList implements ICourseRepository {
    private static List<Course> DB = new ArrayList<Course>();

    @Override
    public boolean saveCourse(Course course) {
        return DB.add(course);
    }

    @Override
    public boolean editCourseByCourseUUID(UUID courseUUID, Course newCourse) {
        if (!courseExistsByCourseUUID(courseUUID)) {
            return false;
        }

        Course oldCourse  = DB.stream()
                                    .filter(course -> course.getCourseUUID() == courseUUID)
                                    .findFirst()
                                    .orElse(null); // We know the request exists at this point in code, so null will never be returned

        DB.set(DB.indexOf(oldCourse), newCourse);
        return true;
    }

    @Override
    public boolean editCourseByUniversityIDAndCourseID(Long universityID, Long courseID, Course newCourse) {
        if (!courseExistsByUniversityIDAndCourseID(universityID, courseID)) {
            return false;
        }

        Course oldCourse = DB.stream()
                                    .filter (   currentCourse -> 
                                                (currentCourse.getUniversityID() == universityID)
                                                && (currentCourse.getCourseID() == courseID)
                                            )
                                    .findFirst()
                                    .orElse(null); // We know the request exists at this point in code, so null will never be returned

        DB.set(DB.indexOf(oldCourse), newCourse);
        return false;
    }

    @Override
    public boolean deleteCourseByCourseUUID(UUID courseUUID) {
        if (!courseExistsByCourseUUID(courseUUID)) {
            return false;
        }
        
        Iterator<Course> deleteCourseIterator = DB.iterator();
        while (deleteCourseIterator.hasNext()) {
            if (deleteCourseIterator.next().getCourseUUID() != courseUUID) continue;
            deleteCourseIterator.remove();
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCourseByUniversityIDAndCourseID(Long universityID, Long courseID) {
        if (!courseExistsByUniversityIDAndCourseID(universityID, courseID)) {
            return false;
        }
        
        Iterator<Course> deleteCourseIterator = DB.iterator();
        while (deleteCourseIterator.hasNext()) {
            Course currentCourse = deleteCourseIterator.next();
            if (currentCourse.getUniversityID() != universityID || currentCourse.getCourseID() != courseID) continue;
            deleteCourseIterator.remove();
            return true;
        }
        
        return false;
    }

    @Override
    public boolean courseExistsByCourseUUID(UUID courseUUID) {
        return DB.stream().filter(currentCourse -> currentCourse.getCourseUUID() == courseUUID).findFirst().isPresent();
    }

    @Override
    public boolean courseExistsByUniversityIDAndCourseID(Long universityID, Long courseID) {
        return DB.stream().filter(  currentCourse -> 
                                    (currentCourse.getUniversityID() == universityID)
                                    && (currentCourse.getCourseID() == courseID)
                                ).findFirst().isPresent();
    }

    @Override
    public List<Course> getAllCourses() {
        return DB;
    }

    @Override
    public List<Course> getAllCoursesByUniversityID(Long universityID) {
        return DB.stream().filter(currentCourse -> currentCourse.getUniversityID().equals(universityID)).collect(Collectors.toList());
    }
    
}
