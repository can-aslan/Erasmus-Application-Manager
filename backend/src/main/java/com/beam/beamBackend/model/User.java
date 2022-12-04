package com.beam.beamBackend.model;

import com.beam.beamBackend.enums.UserType;

public abstract class User{
    long id;
    String name;
    String surname;
    String email;
    long bilkentId;
    String password;
    UserType userType;
}