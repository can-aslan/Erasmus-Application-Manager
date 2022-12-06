package com.beam.beamBackend.model;

import java.util.UUID;

import com.beam.beamBackend.enums.*;
public class Student extends User{

    // Properties
    UUID id;
    String name;
    String surname;
    String email;
    long bilkentId;
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

    // Constructors
    public Student(
        UUID id,
        String name,
        String surname,
        String email,
        long bilkentId,
        String password,
        UserType userType,
        Department department,
        Faculty faculty,
        Department department2,
        Faculty faculty2,
        String telephoneNo,
        boolean hasMinor,
        boolean hasMajor,
        String nationality,
        String dateOfBirth,
        Sex sex
    ){
        super(id, name, surname, email, bilkentId, password, userType);
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.bilkentId = bilkentId;
        this.password = password;
        this.userType = userType;
        this.department = department;
        this.faculty = faculty;
        this.department2 = department2;
        this.faculty2 = faculty2;
        this.telephoneNo = telephoneNo;
        this.hasMajor = hasMajor;
        this.nationality = nationality;
        this.dateOfBirth = dateOfBirth;
        this.sex = sex;
    }


    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getBilkentId() {
        return this.bilkentId;
    }

    public void setBilkentId(long bilkentId) {
        this.bilkentId = bilkentId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return this.userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Faculty getFaculty() {
        return this.faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Department getDepartment2() {
        return this.department2;
    }

    public void setDepartment2(Department department2) {
        this.department2 = department2;
    }

    public Faculty getFaculty2() {
        return this.faculty2;
    }

    public void setFaculty2(Faculty faculty2) {
        this.faculty2 = faculty2;
    }

    public String getTelephoneNo() {
        return this.telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo;
    }

    public boolean isHasMinor() {
        return this.hasMinor;
    }

    public boolean getHasMinor() {
        return this.hasMinor;
    }

    public void setHasMinor(boolean hasMinor) {
        this.hasMinor = hasMinor;
    }

    public boolean isHasMajor() {
        return this.hasMajor;
    }

    public boolean getHasMajor() {
        return this.hasMajor;
    }

    public void setHasMajor(boolean hasMajor) {
        this.hasMajor = hasMajor;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return this.sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }
    
}