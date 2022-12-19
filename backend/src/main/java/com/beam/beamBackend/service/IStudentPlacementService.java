package com.beam.beamBackend.service;

import java.io.IOException;
import java.util.ArrayList;
import com.beam.beamBackend.model.Student;

public interface IStudentPlacementService {
    ArrayList<Student> placeStudents(String department) throws Exception;
    ArrayList<Student> readFromStudentCsv(String department) throws Exception;
    void getAllUniversitiesQuota(String department) throws Exception;
}
