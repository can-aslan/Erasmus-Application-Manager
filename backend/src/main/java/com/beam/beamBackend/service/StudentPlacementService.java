package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.Faculty;
import com.beam.beamBackend.enums.Semester;
import com.beam.beamBackend.enums.Sex;
import com.beam.beamBackend.enums.StudyType;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.Preferences;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IPreferencesRepository;
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

    @Autowired
    final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IUniversityRepository universityRepository;
    private final StudentService studentService;
    private final IStudentRepository studentRepository;
    private final IPreferencesRepository preferencesRepository;
    private final IAccountRepository accountRepository;
    public ArrayList<Student> placeStudents(String department) throws Exception {
        

        readFromStudentCsv(department);
        getAllUniversitiesQuota(department);
        
        //ASSING COORDINATOR TO STUDENTS
        for (int i = 0; i < regiteredStudents.size(); i++){
            ArrayList<String> preferenceList = new ArrayList<>();
            Optional<Preferences> preferences = preferencesRepository.findByStudentBilkentId(regiteredStudents.get(i).getUser().getBilkentId());
            preferenceList.add(preferences.get().getPref1());
            preferenceList.add(preferences.get().getPref2());
            preferenceList.add(preferences.get().getPref3());
            preferenceList.add(preferences.get().getPref4());
            preferenceList.add(preferences.get().getPref5());

            for (int j = 0; j < preferenceList.size(); j++){
                if (preferenceList.get(j) != null){
                    University currentUni = universityRepository.findUniByName(preferenceList.get(j));
                    if(quotas.get(currentUni) > 0){
                        regiteredStudents.get(i).setHostUni(currentUni);
                        quotas.put(currentUni,quotas.get(currentUni) - 1 );
                        break;  
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
        
        return regiteredStudents;
    }



    public ArrayList<Student> readFromStudentCsv(String department) throws Exception{
        try{ 
            String line = "";  
            String splitBy = ",";  

            boolean isHeader = true;
            //parsing a CSV file into BufferedReader class constructor  
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/backend/src/main/resources/sortedStudents"+ department + ".csv"));  
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

                if (accountRepository.existsByBilkentId(bilkentId)){
                    continue;
                }
                String email = lineSplitted[39];
                // What about password

                Faculty f = Faculty.valueOf(lineSplitted[4]);

                Department d = Department.valueOf(lineSplitted[5]);

                Semester semester = Semester.valueOf(lineSplitted[21]);

                Faculty f2;
                if ((lineSplitted[31] != null) && !lineSplitted[31].isEmpty()){
                    f2 = Faculty.valueOf(lineSplitted[31]);
                } else{
                    f2 = null;
                }

                Department d2;
                if ((lineSplitted[33] != null) && !lineSplitted[32].isEmpty()){
                    d2 = Department.valueOf(lineSplitted[32]);
                } else {
                    d2 = null;
                }

                String telephoneNo = lineSplitted[33];
                String nationality = lineSplitted[34];
                String dateOfBirth = lineSplitted[35];
                Sex sex = Sex.valueOf(lineSplitted[36]);
                

                String academicYear = lineSplitted[37];

                StudyType st;
                if ((lineSplitted[39] != null) && !lineSplitted[38].isEmpty()){
                    st = StudyType.valueOf(lineSplitted[38].toUpperCase());
                } else {
                    st = StudyType.NORMAL;
                }

                String password = generatePsw();
                String hashedpassword = encodePassword(password);

                User newUser = new User(UUID.randomUUID(), name, surname, email, bilkentId, hashedpassword, UserType.OUTGOING_STUDENT);
                //University homeUni = universityRepository.findUniByName("Bilkent University");
                University homeUni = null;
                accountRepository.save(newUser);
                newUser = accountRepository.findUserByBilkentId(bilkentId);
                Student newStudent = new Student(UUID.randomUUID(), newUser, d, f, d2, f2, telephoneNo, st, nationality, dateOfBirth, sex, homeUni, null, academicYear, semester, null);
                studentRepository.save(newStudent);

                regiteredStudents.add(newStudent);

                String pref1 = lineSplitted[22];
                String pref2 = lineSplitted[23];    
                String pref3 = lineSplitted[24];
                String pref4 = lineSplitted[25];
                String pref5 = lineSplitted[26]; 
                Preferences preferences = new Preferences(UUID.randomUUID(), newStudent.getUser().getBilkentId(), pref1, pref2, pref3, pref4, pref5);
                preferencesRepository.save(preferences);
                newUser.setPassword(password);
            }  
            br.close();


        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
            throw e;
        }

        System.out.println(regiteredStudents);
        return regiteredStudents;
    }


    /**
     * This method brings quota of all universities from relatedservices and put University and quota and university
     * pairs into the quotas hashmap
     * @param department department whose universites' quotas will be brought
     * @throws Exception
     */
    public void getAllUniversitiesQuota(String department) throws Exception{
        try {
            String line = "";  
            String splitBy = ",";  

            boolean isHeader = true;
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/backend/src/main/resources/UniQuotas "+ department + ".csv"));  

            while ((line = br.readLine()) != null){
                // Skip the first line of the table while registering students
                if (isHeader){
                    isHeader = false;
                    System.out.println("header Skipped");
                    continue;
                }

                String[] lineSplitted = line.split(splitBy);    // use comma as separator  

                String uniName = lineSplitted[0];
                String quotaString = lineSplitted[1];

                int quota = Integer.parseInt(quotaString);
                University currentUniversity = universityRepository.findUniByName(uniName);
                quotas.put(currentUniversity, quota);
            }
        }catch(Exception e){
            e.printStackTrace();  
            throw e;
        }
    }

    private String encodePassword(String plainPassword) {
        try {
            return bCryptPasswordEncoder.encode(plainPassword);
        } catch (Exception e) {
            throw e;
        }
    }

    private String generatePsw(){
        String allChars = "abcdefghijklmnopqprstuvwxyz0123456789";
        String generatedPsw = "";
        for (int i = 0; i< 8; i++){
            int random = (int) Math.random();
            int modulo = random % 35;
            generatedPsw = generatedPsw + allChars.substring(modulo, modulo+1);
        }
        return generatedPsw;
    }

    
    public void readFromUniCsv(String fepartment) throws IOException{
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
    }

        
    

    
}
