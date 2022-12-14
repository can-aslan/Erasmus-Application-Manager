package com.beam.beamBackend.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.Student;
import com.beam.beamBackend.model.University;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IStaffRepository;
import com.beam.beamBackend.repository.IStudentRepository;
import com.beam.beamBackend.repository.IUniversityRepository;
import com.beam.beamBackend.request.StaffRequest;
import com.beam.beamBackend.request.StudentRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StaffService {
    final private IAccountRepository userRepo;
    private final IStaffRepository staffRepository;

    public UUID addStaff(StaffRequest staff) throws Exception {
        try {
            Optional<User> user = userRepo.findUserById(staff.getUserId());

            if (!user.isPresent()) {
                throw new Exception("user not found");
            }

            Staff staffDb = Staff.toStaff(staff, user.get());
            staffDb.setId(UUID.randomUUID());
            System.out.println("helodb: " + staffDb);

            staffRepository.save(staffDb);
            return staffDb.getUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }        
    }

    public Staff getStaffByUserId(UUID id) throws Exception {
        try {
            Optional<Staff> staff = staffRepository.findByUserId(id);

            if (!staff.isPresent()) {
                throw new Exception("user not found");
            }

            return staff.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Staff> getCoordinatorsByDepartment(Department dep) throws Exception {
        try {
            List<Staff> staff = staffRepository.findByDepartmentAndUserUserType(dep, UserType.COORDINATOR);

            if (staff.size() == 0) {
                throw new Exception("no cordinator is found for this department");
            }

            return staff;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Staff> getAllStaff() throws Exception {
        try {
            List<Staff> staff = staffRepository.findAll();

            if (staff.size() == 0) {
                throw new Exception("no staff is found");
            }

            return staff;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public List<Staff> getAllStaffByType(UserType userType) throws Exception {
        try {
            List<Staff> staff = staffRepository.findByUserUserType(userType);

            if (staff.size() == 0) {
                throw new Exception("no staff is found");
            }

            return staff;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
