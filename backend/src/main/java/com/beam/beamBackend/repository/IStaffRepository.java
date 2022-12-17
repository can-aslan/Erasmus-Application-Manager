package com.beam.beamBackend.repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.enums.Department;
import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.Student;

@Repository
public interface IStaffRepository extends JpaRepository<Staff, UUID> {
    Optional<Staff> findById(UUID id);
    Optional<Staff> findByUserBilkentId(Long bilkentId);
    Optional<Staff> findByUserId(UUID id);
    List<Staff> findByDepartmentAndUserUserType(Department department, UserType userType);
    List<Staff> findByUserUserType(UserType userType);
    List<Staff> findAll();
    boolean existsByUserIdAndUserUserType(UUID instructor, UserType userType);
}
