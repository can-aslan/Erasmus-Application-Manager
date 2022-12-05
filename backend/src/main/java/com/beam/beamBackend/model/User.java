package com.beam.beamBackend.model;

import java.util.UUID;
import javax.persistence.Entity;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User{
    
    private UUID id;

    
    private String name;
    private String surname;
    private String email;
    private long bilkentId;
    private String password;
    private UserType userType;

    public User(
                @JsonProperty("name") String name,
                @JsonProperty("surname") String surname,
                @JsonProperty("email") String email,
                @JsonProperty("bilkentId") long bilkentId,
                @JsonProperty("password") String password,
                @JsonProperty("userType") UserType userType) {

        this.name = name;
        this.surname = surname;
        this.email = email;
        this.bilkentId = bilkentId;
        this.password = password;
        this.userType = userType;
    }

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

    public boolean setUUID(UUID uuid) {
        if (this.id != null) {
            this.id = uuid;
            return true;
        }

        return false;        
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public long getBilkentId() {
        return bilkentId;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }
}