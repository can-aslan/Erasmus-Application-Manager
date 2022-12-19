package com.beam.beamBackend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IStaffRepository;
import com.beam.beamBackend.request.StaffRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class StaffService implements IStaffService {
    private final IAccountRepository userRepo;
    private final IStaffRepository staffRepository;

    @Override
    public UUID addStaff(StaffRequest staff) throws Exception {
        try {
            Optional<User> user = userRepo.findUserById(staff.getUserId());

            if (!user.isPresent()) {
                throw new Exception("user not found");
            }

            Staff staffDb = Staff.toStaff(staff, user.get());
            // staffDb.setId(UUID.randomUUID());
            System.out.println("helodb: " + staffDb);

            staffRepository.save(staffDb);
            return staffDb.getUser().getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }        
    }

    @Override
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
    
    @Override
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
    
    @Override
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
    
    @Override
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
