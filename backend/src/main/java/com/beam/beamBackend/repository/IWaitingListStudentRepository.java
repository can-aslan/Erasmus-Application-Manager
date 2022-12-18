package com.beam.beamBackend.repository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.PreApprovalForm;
import com.beam.beamBackend.model.WaitingListStudent;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface IWaitingListStudentRepository extends JpaRepository<WaitingListStudent, UUID>{
    Optional<WaitingListStudent> findByStudentUserBilkentId(Long studentBilkentId);

}