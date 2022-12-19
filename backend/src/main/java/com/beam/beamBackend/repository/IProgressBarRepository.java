package com.beam.beamBackend.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.beam.beamBackend.model.ProgressBar;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface IProgressBarRepository extends JpaRepository<ProgressBar, UUID> {
    Optional<ProgressBar> findByStudentUserBilkentId(Long bilkentId);
}
