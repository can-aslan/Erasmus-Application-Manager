package com.beam.beamBackend.request;

import jakarta.persistence.Entity;

import lombok.Data;

@Data
// @Entity
public class ChangePassword {
    private String oldPassword;
    private String newPassword;
}
