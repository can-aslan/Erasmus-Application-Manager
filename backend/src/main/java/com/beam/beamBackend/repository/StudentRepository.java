package com.beam.beamBackend.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.Student;

@Repository
public interface StudentRepository
//extends JpaRepository<Student, Long>
{
    //boolean preApprovalStatus = findStudentById(long id)
}
