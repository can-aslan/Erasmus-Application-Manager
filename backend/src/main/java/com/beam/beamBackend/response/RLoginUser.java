package com.beam.beamBackend.response;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.beam.beamBackend.enums.UserType;
import com.beam.beamBackend.model.User;

import lombok.Data;

@Data
@Entity //add bilkent id
public class RLoginUser {
    @Id
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private UserType userType;
    private String accessToken;
    private String refreshToken;

    public RLoginUser(User user, String accessToken, String refreshToken) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
