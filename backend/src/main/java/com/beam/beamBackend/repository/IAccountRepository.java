package com.beam.beamBackend.repository;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.beam.beamBackend.model.User;

// @Qualifier("accountjpa")
@Repository
@Transactional
public interface IAccountRepository extends JpaRepository<User, UUID> {
    //List<User> findAll();
    List<User> findAllById(UUID id);
    //List<User> findAllByBilkentId(Long bilkentId);
    User findUserByBilkentId(Long bilkentId);
    Optional<User> findUserById(UUID id);
    Optional<User> findUserByEmail(String email);
    boolean existsByBilkentId(Long bilkentId);
    /*
        @Query("SELECT phoneNumber FROM CustomerEntity customer")
        List<String> findPhoneNumbers();
    */

    @Modifying
    @Query("update User u set u.password = :password where u.id = :id")
    int updatePassword(@Param("password") String newPassword, @Param("id") UUID id);
}