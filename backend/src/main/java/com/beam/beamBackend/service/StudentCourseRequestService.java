package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.Course;
import com.beam.beamBackend.model.File;

@Service
// @RequiredArgsConstructor (import lombok.RequiredArgsConstructor;)
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
        return false;
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
        return false;
    }

    @Override
    public ArrayList<Course> getPreviouslyRequestedCourses() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
