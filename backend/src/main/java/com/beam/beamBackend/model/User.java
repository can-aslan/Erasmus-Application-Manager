package com.beam.beamBackend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.beam.beamBackend.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_table")
@NoArgsConstructor
public class User {
    @Id
    // @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotBlank
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "bilkent_id", nullable = false)
    private Long bilkentId;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    public User(@JsonProperty("id") UUID id,
                @JsonProperty("name") String name,
                @JsonProperty("surname") String surname,
                @JsonProperty("email") String email,
                @JsonProperty("bilkentId") Long bilkentId,
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

    // do not send password to client
    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
