package com.beam.beamBackend.request;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class ChangePassword {
    private String oldPassword;
    private String newPassword;
}
