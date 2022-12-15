package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;
import com.beam.beamBackend.enums.Semester;
import com.beam.beamBackend.enums.Sex;
import com.beam.beamBackend.enums.StudyType;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;

import lombok.RequiredArgsConstructor;


import java.io.BufferedReader;  
import java.io.FileReader;  
import java.io.IOException;  


@Service
@RequiredArgsConstructor
public class StudentPlacementService {
    private Hashtable<University, Integer> quotas;

    public void placeStudents() {
        
    }



    public void readFromCsv(){
        ArrayList<Student> regiteredStudents = new ArrayList<>();

        String line = "";  
        String splitBy = ",";  
        try{  
            boolean isHeader = true;
            //parsing a CSV file into BufferedReader class constructor  
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/backend/src/main/resources/sortedStudents.csv"));  
            while ((line = br.readLine()) != null)   //returns a Boolean value  
            {  
                // Skip the first line of the table while registering students
                if (isHeader){
                    isHeader = false;
                    continue;
                }
                
                Student newStudent = new Student();
                String[] lineSplitted = line.split(splitBy);    // use comma as separator  

                newStudent.getUser().setName(lineSplitted[1]);
                newStudent.getUser().setSurname(lineSplitted[2]);
                newStudent.getUser().setBilkentId(Long.parseLong(lineSplitted[3]));

                Faculty f = Faculty.valueOf(lineSplitted[4]);
                newStudent.setFaculty(f);

                Department d = Department.valueOf(lineSplitted[5]);
                newStudent.setDepartment(d);

                // TODO add degree property to Student where degree is an enum

                // Transcript grade property is passed (4/4)
                //  Transcript grade property (100/100) is passed 
                // Transcript grade contribution property is passed
                // TODO erasmusPoint propery to Student

                Semester s = Semester.valueOf(lineSplitted[20]);
                newStudent.setSemester(s);

                // TODO 5 preferences to Student

                if ((lineSplitted[32] != null) && !lineSplitted[32].isEmpty()){
                    Faculty f2 = Faculty.valueOf(lineSplitted[32]);
                    newStudent.setFaculty2(f2);
                } else{
                    newStudent.setFaculty2(null);
                }

                if ((lineSplitted[33] != null) && !lineSplitted[33].isEmpty()){
                    Department d2 = Department.valueOf(lineSplitted[33]);
                    newStudent.setDepartment2(d2);
                } else {
                    newStudent.setDepartment2(null);
                }

                newStudent.setTelephoneNo(lineSplitted[34]);
                newStudent.setNationality(lineSplitted[35]);
                newStudent.setDateOfBirth(lineSplitted[36]);
                
                Sex sex = Sex.valueOf(lineSplitted[37]);
                newStudent.setSex(sex);

                newStudent.setAcademicYear(lineSplitted[38]);

                if ((lineSplitted[39] != null) && !lineSplitted[39].isEmpty()){
                    StudyType st = StudyType.valueOf(lineSplitted[39]);
                    newStudent.setStudyType(st);
                } else {
                    newStudent.setStudyType(null);
                }


                regiteredStudents.add(newStudent);
            }  
            br.close();
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }

        System.out.println(regiteredStudents);
    }

        
    

    
}
