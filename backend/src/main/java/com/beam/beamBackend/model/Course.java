package com.beam.beamBackend.model;

import com.beam.beamBackend.enums.*;

public abstract class Course {
    String courseCode;
    String coursename;
    Department department;
    long id;
    double ects;
    String syllabus; // shouldn't it be a file
    long uniId;
}
