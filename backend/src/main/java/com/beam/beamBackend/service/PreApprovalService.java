package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.beam.beamBackend.enums.PreApprovalStatus;
import com.beam.beamBackend.model.PreApprovalForm;
import com.beam.beamBackend.model.Staff;
import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.repository.IPreApprovalRepository;
import com.beam.beamBackend.service.form.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PreApprovalService implements IPreApprovalService {
    private final IPreApprovalRepository preApprovalRepository;
    private final IAccountRepository accountRepository;
    private final FormService formService;

    @Override
    public List<PreApprovalForm> getCoordinatorPreApprovals(@Valid UUID coordinatorUserId) throws Exception {
        try {
            boolean userExist = accountRepository.existsById(coordinatorUserId);

            if (!userExist) {
                throw new Exception("user not found");
            }

            return preApprovalRepository.findAllByStudentCoordinatorUserId(coordinatorUserId);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean approvePreApprovals(@Valid UUID coordinatorUserId, @Valid Long studentId) throws Exception {
        try {
            Optional<User> coordinator = accountRepository.findById(coordinatorUserId);
            Optional<PreApprovalForm> preApproval = preApprovalRepository.findByStudentUserBilkentId(studentId);

            if (!preApproval.isPresent()) {
                throw new Exception("PreApproval for the student is not found!");
            }

            if (!coordinator.isPresent()) {
                throw new Exception("Coordinator is not found!");
            }

            formService.signPreApproval(studentId, coordinator.get().getBilkentId());
            PreApprovalForm preApprovalObj = preApproval.get();
            preApprovalObj.setPreApprovalStatus(PreApprovalStatus.COORDINATOR_APPROVED);
            preApprovalRepository.save(preApprovalObj);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean rejectPreApprovals(@Valid UUID coordinatorUserId, @Valid Long studentId) throws Exception {
        try {
            Optional<User> coordinator = accountRepository.findById(coordinatorUserId);
            Optional<PreApprovalForm> preApproval = preApprovalRepository.findByStudentUserBilkentId(studentId);

            if (!preApproval.isPresent()) {
                throw new Exception("PreApproval for the student is not found!");
            }

            if (!coordinator.isPresent()) {
                throw new Exception("Coordinator is not found!");
            }

            PreApprovalForm preApprovalObj = preApproval.get();
            preApprovalObj.setPreApprovalStatus(PreApprovalStatus.COORDINATOR_REJECTED);
            preApprovalRepository.save(preApprovalObj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    // use formService.signApproval to sign approval
}
