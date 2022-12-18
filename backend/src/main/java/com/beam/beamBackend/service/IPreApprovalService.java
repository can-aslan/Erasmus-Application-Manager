package com.beam.beamBackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.beam.beamBackend.model.PreApprovalForm;
import jakarta.validation.Valid;

public interface IPreApprovalService {
    List<PreApprovalForm> getCoordinatorPreApprovals(@Valid UUID coordinatorUserId) throws Exception;
    boolean approvePreApprovals(@Valid UUID coordinatorUserId, @Valid Long studentId) throws Exception;
    boolean rejectPreApprovals(@Valid UUID coordinatorUserId, @Valid Long studentId) throws Exception;
}
