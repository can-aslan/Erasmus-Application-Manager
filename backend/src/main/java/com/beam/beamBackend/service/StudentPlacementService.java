package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;
import com.beam.beamBackend.enums.Semester;
import com.beam.beamBackend.enums.Sex;
import com.beam.beamBackend.enums.StudyType;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import com.beam.beamBackend.request.StudentRequest;
import com.beam.beamBackend.request.UserRequest;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;  
import java.io.IOException;  


@Service
@RequiredArgsConstructor
public class StudentPlacementService {
    private Hashtable<University, Integer> quotas;
    ArrayList<Student> regiteredStudents = new ArrayList<>();
    ArrayList<Student> waitingList = new ArrayList<>();

    private final IUniversityRepository universityRepository;
    private final AccountService accountService;
    private final StudentService studentService;
    private final IStudentRepository studentRepository;
    public void placeStudents(Department department) throws Exception {
        ArrayList<String> preferences = new ArrayList<>();
        readFromStudentCsv();
        getAllUniversitiesQuota(department);
        for (int i = 0; i < regiteredStudents.size(); i++){
            //preferences = studentRepository.findById(regiteredStudents.get(i).getId()).get().getPreferences();
            for (int j = 0; j < preferences.size(); j++){
                if (preferences.get(j) != null){
                    University currentUni = universityRepository.findUniByName(preferences.get(j));
                    if(quotas.get(currentUni) > 0){
                        regiteredStudents.get(i).setHostUni(currentUni);
                        quotas.put(currentUni,quotas.get(currentUni) - 1 );
                    }
                }
            }
        }

        // Waiting list creation 
        // If a registeredstudent's hostUn property is null that means they are in the waiting list
        for (int i = 0; i < regiteredStudents.size(); i++){
            Student currentStudent = regiteredStudents.get(i);
            if (currentStudent.getHostUni() == null){
                waitingList.add(currentStudent);
            }
        }
        
        //readFromUniCsv();
    }



    public void readFromStudentCsv() throws Exception{
        System.out.println("--------------------1--------------------");

        try{ 
            String line = "";  
            String splitBy = ",";  

            boolean isHeader = true;
            //parsing a CSV file into BufferedReader class constructor  
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/backend/src/main/resources/sortedStudents.csv"));  
            while ((line = br.readLine()) != null)   //returns a Boolean value  
            {  
                // Skip the first line of the table while registering students
                if (isHeader){
                    isHeader = false;
                    System.out.println("header Skipped");
                    continue;
                }

                String[] lineSplitted = line.split(splitBy);    // use comma as separator  

                String name = lineSplitted[1];
                String surname = lineSplitted[2];
                Long bilkentId = Long.parseLong(lineSplitted[3]);
                String email = lineSplitted[40];
                System.out.println("--------------------2--------------------");

                UserRequest newUserRequest = new UserRequest(null ,name, surname, email, bilkentId, UserType.OUTGOING_STUDENT); 
                System.out.println("--------------------2.1--------------------");
                User newUser = accountService.addUserWithUserRequest(newUserRequest);
                System.out.println("--------------------2.2--------------------");
                StudentRequest sReq = new StudentRequest(newUser.getId(), null, null, null, null, null, null, null, null, null, null, null, null, null, null);
                System.out.println("--------------------2.3--------------------");
                Student.toStudent(sReq, newUser, universityRepository.findUniByName("Bilkent Univerity"), null, null);

                //-------------------------------------Student creation starts here------------------
                Optional<Student> newStudent = studentRepository.findById(newUser.getId());

                if (!newStudent.isPresent()){
                    throw new Exception("The student is not found!");
                }
                System.out.println("--------------------3--------------------");

                Faculty f = Faculty.valueOf(lineSplitted[4]);
                newStudent.get().setFaculty(f);

                Department d = Department.valueOf(lineSplitted[5]);
                newStudent.get().setDepartment(d);

                // TODO add degree property to Student where degree is an enum

                // TODO erasmusPoint propery to Student

                Semester s = Semester.valueOf(lineSplitted[20]);
                newStudent.get().setSemester(s);

                // TODO 5 preferences to Student

                if ((lineSplitted[32] != null) && !lineSplitted[32].isEmpty()){
                    Faculty f2 = Faculty.valueOf(lineSplitted[32]);
                    newStudent.get().setFaculty2(f2);
                } else{
                    newStudent.get().setFaculty2(null);
                }

                if ((lineSplitted[33] != null) && !lineSplitted[33].isEmpty()){
                    Department d2 = Department.valueOf(lineSplitted[33]);
                    newStudent.get().setDepartment2(d2);
                } else {
                    newStudent.get().setDepartment2(null);
                }
                System.out.println("--------------------4--------------------");

                newStudent.get().setTelephoneNo(lineSplitted[34]);
                newStudent.get().setNationality(lineSplitted[35]);
                newStudent.get().setDateOfBirth(lineSplitted[36]);
                
                Sex sex = Sex.valueOf(lineSplitted[37]);
                newStudent.get().setSex(sex);

                newStudent.get().setAcademicYear(lineSplitted[38]);

                if ((lineSplitted[39] != null) && !lineSplitted[39].isEmpty()){
                    StudyType st = StudyType.valueOf(lineSplitted[39]);
                    newStudent.get().setStudyType(st);
                } else {
                    newStudent.get().setStudyType(null);
                }


                regiteredStudents.add(newStudent.get());
            }  
            br.close();
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
            throw e;
        }

        System.out.println(regiteredStudents);
    }


    /**
     * This method brings quota of all universities from relatedservices and put University and quota and university
     * pairs into the quotas hashmap
     * @param department department whose universites' quotas will be brought
     */
    public void getAllUniversitiesQuota(Department department){
    }

    
    /*public void readFromUniCsv() throws IOException{
        try {
            String line = "";  
            String splitBy = ",";  
    
    
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/backend/src/main/resources/UniQuotas.csv"));  
            while ((line = br.readLine()) != null){
                String[] lineSplitted = line.split(splitBy);    // use comma as separator
                
                boolean isHeader = true;
                // Skip the first line of the table while registering students
                if (isHeader){
                    isHeader = false;
                    continue;
                }

                // Creation of universities
                //quotas.put()
            }  
        }catch (Exception e){
            e.printStackTrace();  
            throw e;
        }
    }*/

        
    

    
}
