package com.beam.beamBackend.controller;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beam.beamBackend.model.PreApprovalForm;
import com.beam.beamBackend.model.Wishlist;
import com.beam.beamBackend.response.Response;
import com.beam.beamBackend.service.PreApprovalService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/preapproval")
public class PreApprovalController {
    private final PreApprovalService preApprovalService;

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("coordinator/{coordinatorUserId}")
    public ResponseEntity<Object> getCoordinatorPreApprovals(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId){
        try {
            ArrayList<PreApprovalForm> preApprovalForms = preApprovalService.getCoordinatorPreApprovals(coordinatorUserId);
            return Response.create("PreApprovals are fetchec!", HttpStatus.OK,preApprovalForms );
        } catch (Exception e){
            return Response.create("PreApprovals could not fetched!", 499 );
        }
    }


    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("coordinator/{coordinatorUserId}/student/{studentId}/approve")
    public ResponseEntity<Object> approvePreApproval(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId, @Valid @PathVariable("studentId") Long studentId){
        try {
            boolean isSuccessfull = preApprovalService.approvePreApprovals(coordinatorUserId, studentId);
            return Response.create("PreApprovals is approved!", HttpStatus.OK,isSuccessfull );
        } catch (Exception e){
            return Response.create("PreApprovals could not approved!", 499 );
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
    @GetMapping("coordinator/{coordinatorUserId}/student/{studentId}/reject")
    public ResponseEntity<Object> rejectPreApproval(@Valid @PathVariable("coordinatorUserId") UUID coordinatorUserId, @Valid @PathVariable("studentId") Long studentId){
        try {
            boolean isSuccessfull = preApprovalService.rejectPreApprovals(coordinatorUserId, studentId);
            return Response.create("PreApprovals is rejected!", HttpStatus.OK,isSuccessfull );
        } catch (Exception e){
            return Response.create("PreApprovals could not rejected!", 499 );
        }
    }
}
