package com.beam.beamBackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.exception.ExceptionLogger;
import com.beam.beamBackend.model.PreApprovalForm;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.IPreApprovalService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * A Spring Controller class to handle preapproval retrieval, approval, and rejection
 */
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/preapproval")
public class PreApprovalController {
    private final IPreApprovalService preApprovalService;

    /**
     * This method calls related service method to get related pre approval forms to
     * the coordinator.
     * 
     * @param coordinatorUserId user id of the coordinator whose preapproval are retrieved
     * @return list of preApprovalForm's related to the coordinator.
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("/coordinator/{coordinatorUserId}")
    public ResponseEntity<Object> getCoordinatorPreApprovals(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId) {
        try {
            List<PreApprovalForm> preApprovalForms = preApprovalService.getCoordinatorPreApprovals(coordinatorUserId);
            return Response.create("PreApprovals are fetched!", HttpStatus.OK, preApprovalForms);
        }
        catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);
        }
    }

    /**
     * This method calls related service method to approve related pre approval forms to
     * the coordinator.
     * 
     * @param coordinatorUserId user id of the coordinator who approves the pre approval form
     * @param studentId bilkent id of the student whose pre approval form is be approved 
     * @return if the form successfully rejected or not
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("coordinator/{coordinatorUserId}/student/{studentId}/approve")
    public ResponseEntity<Object> approvePreApproval(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId, @Valid @PathVariable("studentId") Long studentId) {
        try {
            boolean isSuccessfull = preApprovalService.approvePreApprovals(coordinatorUserId, studentId);
            return Response.create("PreApprovals are approved!", HttpStatus.OK, isSuccessfull);
        }
        catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);
        }
    }

    /**
     * This method calls related service method to reject related pre approval forms to
     * the coordinator.
     * 
     * @param coordinatorUserId user id of the coordinator who rejects the pre approval form
     * @param studentId bilkent id of the student whose pre approval form is be rejected 
     * @return if the form successfully rejected or not
     */
    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @PostMapping("coordinator/{coordinatorUserId}/student/{studentId}/reject")
    public ResponseEntity<Object> rejectPreApproval(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId, @Valid @PathVariable("studentId") Long studentId) {
        try {
            boolean isSuccessfull = preApprovalService.rejectPreApprovals(coordinatorUserId, studentId);
            return Response.create("PreApprovals are rejected!", HttpStatus.OK, isSuccessfull);
        }
        catch (Exception e) {
            return Response.create(ExceptionLogger.log(e), 499);
        }
    }
}
