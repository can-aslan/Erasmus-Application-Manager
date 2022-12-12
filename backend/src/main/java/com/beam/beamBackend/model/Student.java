package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends User{

    // Properties
    UUID id;
    String name;
    String surname;
    String email;
    Long bilkentId;
    String password;
    UserType userType;
    Department department;
    Faculty faculty;
    Department department2;
    Faculty faculty2;
    String telephoneNo;
    boolean hasMinor;
    boolean hasMajor;
    String nationality;
    String dateOfBirth;
    Sex sex;
}