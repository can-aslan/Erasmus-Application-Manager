package com.beam.beamBackend.model;

import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class User{
    @Id
    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    private String email;

    @NotNull
    private long bilkentId;

    @NotEmpty
    private String password;

    @NotNull
    private UserType userType;

    // public User(
    //             @JsonProperty("name") String name,
    //             @JsonProperty("surname") String surname,
    //             @JsonProperty("email") String email,
    //             @JsonProperty("bilkentId") long bilkentId,
    //             @JsonProperty("password") String password,
    //             @JsonProperty("userType") UserType userType) {

    //     this.name = name;
    //     this.surname = surname;
    //     this.email = email;
    //     this.bilkentId = bilkentId;
    //     this.password = password;
    //     this.userType = userType;
    // }

    // public User(
    //             @JsonProperty("name") String name) {

    //     this.name = name;
    // }

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

    public void setUUID(UUID uuid) {
        this.id = uuid;        
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