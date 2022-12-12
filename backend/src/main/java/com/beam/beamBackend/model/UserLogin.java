package com.beam.beamBackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLogin {
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