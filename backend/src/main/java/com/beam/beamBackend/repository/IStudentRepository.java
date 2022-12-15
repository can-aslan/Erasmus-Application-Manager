package com.beam.beamBackend.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.Student;

@Repository
public interface IStudentRepository extends JpaRepository<Student, UUID> {
    Optional<Student> findById(UUID id);
    Optional<Student> findByUserBilkentId(Long bilkentId);
    Optional<Student> findByUserId(UUID id);
    List<Student> findAll();
}
