package com.beam.beamBackend.repository;

import java.util.UUID;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.User;

@Qualifier("accountjpa")
@Repository
public interface IAccountRepository extends JpaRepository<User, UUID> {
    //List<User> findAll();
    List<User> findAllById(UUID id);
    //List<User> findAllByBilkentID(Long bilkentID);
    //User findUserByBilkentID(Long bilkentID);
    //boolean existsByBilkentID(Long bilkentID);
    /*
        @Query("SELECT phoneNumber FROM CustomerEntity customer")
        List<String> findPhoneNumbers();
    */
}