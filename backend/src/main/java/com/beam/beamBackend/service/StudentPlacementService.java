package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
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
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IPreferencesRepository;
import com.beam.beamBackend.repository.IStaffRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import lombok.RequiredArgsConstructor;
import java.io.BufferedReader;
import java.io.FileReader;  
import java.io.IOException;  

@Service
@RequiredArgsConstructor
public class StudentPlacementService implements IStudentPlacementService {
    private Hashtable<University, Integer> quotas = new Hashtable<>();
    ArrayList<Student> registeredStudents = new ArrayList<>();
    ArrayList<Student> waitingList = new ArrayList<>();
    
    @Autowired
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final IUniversityRepository universityRepository;
    // private final StudentService studentService;
    private final IStudentRepository studentRepository;
    private final IPreferencesRepository preferencesRepository;
    private final IAccountRepository accountRepository;
    private final IStaffRepository staffRepository;
    private final AccountService accountService;

    @Override
    public ArrayList<Student> placeStudents(String department) throws Exception {
        // Get List of coordinators
        List<Staff> coordinators = staffRepository.findByDepartmentAndUserUserType(Department.valueOf(department), UserType.COORDINATOR);
        int assignedCoordinators = 0;
        readFromStudentCsv(department);
        getAllUniversitiesQuota(department);
        
        //Assign coordinator to students
        for (int i = 0; i < registeredStudents.size(); i++) {
            ArrayList<String> preferenceList = new ArrayList<>();
            Optional<Preferences> preferences = preferencesRepository.findByStudentBilkentId(registeredStudents.get(i).getUser().getBilkentId());

            if ( !preferences.isPresent()) {
                throw new Exception("Preferences for the given student does not exist!");
            }

            preferenceList.add(preferences.get().getPref1());
            preferenceList.add(preferences.get().getPref2());
            preferenceList.add(preferences.get().getPref3());
            preferenceList.add(preferences.get().getPref4());
            preferenceList.add(preferences.get().getPref5());

            for (int j = 0; j < preferenceList.size(); j++) {

                if (preferenceList.get(j) != null && !preferenceList.get(j).equals("")) {
                    University currentUni = universityRepository.findUniByName(preferenceList.get(j));
                    if ( currentUni == null){
                        throw new Exception("The given university does not exist!");
                    }
                    System.out.println(currentUni);
                    if (quotas.get(currentUni) > 0) {
                        registeredStudents.get(i).setHostUni(currentUni);
                        quotas.put(currentUni,quotas.get(currentUni) - 1);
                        
                        System.out.println("==========================================================");
                        //Assign coordinator to students
                        Staff currentCoordinator = coordinators.get( assignedCoordinators % coordinators.size());
                        System.out.println(currentCoordinator);
                        registeredStudents.get(i).setCoordinator(currentCoordinator);

                        break;  
                    }
                }
            }
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        // Waiting list creation 
        // If a registeredstudent's hostUn property is null that means they are in the waiting list
        for (int i = 0; i < registeredStudents.size(); i++){
            Student currentStudent = registeredStudents.get(i);
            if (currentStudent.getHostUni() == null) {
                waitingList.add(currentStudent);
            }
        }

        for (Student s: registeredStudents) {
            if (!studentRepository.existsById(s.getId())){
                studentRepository.save(s);
            }
            else {
                System.out.println("The student with id " + s.getUser().getBilkentId() + " is passed!");
            }
        }

        System.out.println("---------------------------------------------------------");
        
        return registeredStudents;
    }

    @Override
    public ArrayList<Student> readFromStudentCsv(String department) throws Exception {
        try{ 
            String line = "";  
            String splitBy = ",";  

            boolean isHeader = true;
            //parsing a CSV file into BufferedReader class constructor
            System.out.println("*****************************************" + System.getProperty("user.dir")+ "***********************");  
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/backend/src/main/resources/sortedStudents"+ department + ".csv"));  
            while ((line = br.readLine()) != null)   //returns a Boolean value  
            {  
                // Skip the first line of the table while registering students
                if (isHeader) {
                    isHeader = false;
                    System.out.println("header Skipped");
                    continue;
                }

                String[] lineSplitted = line.split(splitBy);    // use comma as separator  
                String name = lineSplitted[1];
                String surname = lineSplitted[2];
                Long bilkentId = Long.parseLong(lineSplitted[3]);

                if (accountRepository.existsByBilkentId(bilkentId)) {
                    continue;
                }

                String email = lineSplitted[39];
                // What about password

                Faculty f = Faculty.valueOf(lineSplitted[4]);
                Department d = Department.valueOf(lineSplitted[5]);
                Semester semester = Semester.valueOf(lineSplitted[21]);

                Faculty f2;
                if ((lineSplitted[31] != null) && !lineSplitted[31].isEmpty()) {
                    f2 = Faculty.valueOf(lineSplitted[31]);
                } else {
                    f2 = null;
                }

                Department d2;
                if ((lineSplitted[33] != null) && !lineSplitted[32].isEmpty()) {
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
                if ((lineSplitted[39] != null) && !lineSplitted[38].isEmpty()) {
                    st = StudyType.valueOf(lineSplitted[38].toUpperCase());
                } else {
                    st = StudyType.NORMAL;
                }

                //String password = generatePsw();
                String password = "beam";
                String hashedpassword = encodePassword(password);
                User newUser = new User(UUID.randomUUID(), name, surname, email, bilkentId, hashedpassword, UserType.OUTGOING_STUDENT);
                University homeUni = universityRepository.findUniByName("Bilkent University");
                if (!accountRepository.existsByBilkentId(bilkentId)) {
                    accountRepository.save(newUser);
                    //accountService.addUser(newUser);
                } else {
                    throw new Exception("The user already exists!");
                }
                newUser = accountRepository.findUserByBilkentId(bilkentId);
                Student newStudent = new Student(UUID.randomUUID(), newUser, d, f, d2, f2, telephoneNo, st, nationality, dateOfBirth, sex, homeUni, null, academicYear, semester, null);

                registeredStudents.add(newStudent);

                String pref1 = lineSplitted[22];
                String pref2 = lineSplitted[23];    
                String pref3 = lineSplitted[24];
                String pref4 = lineSplitted[25];
                String pref5 = lineSplitted[26]; 

                Optional<Preferences> p = preferencesRepository.findByStudentBilkentId(bilkentId);
                if (!p.isPresent()) {
                    Preferences preferences = new Preferences(UUID.randomUUID(), newStudent.getUser().getBilkentId(), pref1, pref2, pref3, pref4, pref5);
                    preferencesRepository.save(preferences);
                }
                
                //newUser.setPassword(password);
            }  

            br.close();

        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;
        }

        return registeredStudents;
    }

    /**
     * This method brings quota of all universities from relatedservices and put University and quota and university
     * pairs into the quotas hashmap
     * @param department department whose universites' quotas will be brought
     * @throws Exception
     */
    @Override
    public void getAllUniversitiesQuota(String department) throws Exception {
        try {
            String line = "";  
            String splitBy = ",";  

            boolean isHeader = true;
            BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/backend/src/main/resources/UniQuotas" + department + ".csv"));  

            while ((line = br.readLine()) != null) {
                // Skip the first line of the table while registering students
                if (isHeader) {
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

            br.close();
        } catch (Exception e) {
            e.printStackTrace();  
            throw e;
        }
    }
    

    public void readFromUniCsv(String fepartment) throws IOException {
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

            br.close();
        } catch (Exception e) {
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
    
    public static String generatePsw() {
        String allChars = "abcdefghijklmnopqprstuvwxyz0123456789";
        String generatedPsw = "";
        for (int i = 0; i < 8; i++){
            int random = (int) (Math.random() * 1000);
            int modulo = random % 36;
            generatedPsw = generatedPsw + allChars.substring(modulo, modulo+1);
        }
        return generatedPsw;
    }
}