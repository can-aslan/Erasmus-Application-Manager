package com.beam.beamBackend.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "preferences_table")
@NoArgsConstructor
@AllArgsConstructor
public class Preferences {

    
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", nullable = false)
    UUID id; 

    @Column(name = "studentBilkentId")
    Long studentBilkentId;

    @Column(name = "pref1")
    String pref1;
    
    @Column(name = "pref2")
    String pref2;
    
    @Column(name = "pref3")
    String pref3;
    
    @Column(name = "pref4")
    String pref4;
    
    @Column(name = "pref5")
    String pref5;


}
