package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.request.StaffRequest;

public interface IStaffService {
    UUID addStaff(StaffRequest staff) throws Exception;
    Staff getStaffByUserId(UUID id) throws Exception;
    List<Staff> getCoordinatorsByDepartment(Department dep) throws Exception;
    List<Staff> getAllStaff() throws Exception;
    List<Staff> getAllStaffByType(UserType userType) throws Exception;
}
