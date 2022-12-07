package com.beam.beamBackend.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class UserLogin {
    @Id
    @NotNull
    private String bilkentId;

    @NotEmpty
    private String password;

    public UserLogin(
                @JsonProperty("bilkentId") String bilkentId,
                @JsonProperty("password") String password) {

        this.bilkentId = bilkentId;
        this.password = password;
    }
}