package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User{
    
    UUID id;
    String name;
    String surname;
    String email;
    long bilkentId;
    String password;
    UserType userType;

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("surname") String surname,
                @JsonProperty("email") String email,
                @JsonProperty("bilkentId") long bilkentId,
                @JsonProperty("password") String password,
                @JsonProperty("userType") UserType userType) {

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.bilkentId = bilkentId;
        this.password = password;
        this.userType = userType;
    }
}