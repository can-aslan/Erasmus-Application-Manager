package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.File;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentCourseRequestService implements IStudentCourseRequestService {

    @Override
    public boolean requestCourse(
        UUID studentId,
        String code,
        String name,
        String bilkentCourse,
        String webpage)
    {
        // TODO Auto-generated method stub
        return (code.length() != 0)
            && (name.length() != 0)
            && (bilkentCourse.length() != 0)
            && (webpage.length() != 0); // temporary for test purposes
    }

    @Override
    public boolean requestCourse(
        UUID studentId,
        String code,
        String name,
        String bilkentCourse,
        String webpage,
        File additionalInfo) 
    {
        // TODO Auto-generated method stub
        return (code.length() != 0)
            && (name.length() != 0)
            && (bilkentCourse.length() != 0)
            && (webpage.length() != 0); // temporary for test purposes
    }

    @Override
    public ArrayList<Course> getPreviouslyRequestedCourses() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
