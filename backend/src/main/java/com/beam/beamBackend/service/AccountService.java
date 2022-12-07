package com.beam.beamBackend.service;

import java.security.SecureRandom;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.repository.AccountRepository;
import com.beam.beamBackend.response.RToken;
import com.beam.beamBackend.response.RUserList;
import com.beam.beamBackend.response.ResponseId;
import com.beam.beamBackend.security.JWTUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {
    public static int hashStrength = 10;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(hashStrength, new SecureRandom());

    private final AccountRepository accountRepository;

    @Autowired
    private final JWTUserService jwtUserService;

    @Autowired
    private final JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authManager;

    // @Autowired
    // public AccountService(AuthenticationManager authManager, int hash) {
    //     this(accountRepository, jwtUserService, jwtUtils, authManager);
    //     passwordEncoder  = new BCryptPasswordEncoder(hashStrength, new SecureRandom());
    // }

    public RToken login(UserLogin user) throws Exception {
        try {
            // passwordEncoder  = new BCryptPasswordEncoder(hashStrength, new SecureRandom());
            String hashedPassword = accountRepository.getPasswordIfUserExist(Long.parseLong(user.getBilkentId()));
            boolean passwordMatch = passwordEncoder.matches(user.getPassword(), hashedPassword);

            if (!passwordMatch) {
                throw new Exception();
            } else {
                System.out.println("paswor match");

                // authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getBilkentId(), user.getPassword()));

                final UserDetails userDetails = jwtUserService.loadUserByUsername(user.getBilkentId());
                final String token = jwtUtils.createToken(userDetails);
                return new RToken(token);
            }
        } catch (Exception e) {
            System.out.println("alo krdsm calisir misin");
            e.printStackTrace();
            throw e;
        }
    }

    public ResponseId addUser(User user) throws Exception {
        try {
            boolean userExist = accountRepository.userExist(user.getBilkentId());
            
            if (userExist) {
                throw new Exception();
            } else {
                // generate uuid and hash password if user does not exist in the system
                user.setUUID(UUID.randomUUID());
                user.setPassword(encodePassword(user.getPassword()));

                return accountRepository.insertUser(user);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean addUserChunk(User[] users) throws Exception {
        // HashSet<User> usersSet = new HashSet<>(Arrays.asList(users));
        // HashSet<User> removedUsers = new HashSet<>();

        for (User user : users) {
            try {
                addUser(user);
            } catch (Exception e) {
                throw e;
            }
        }

        return true;

        // for (User user : usersSet) {
        //     try {
        //         user.setUUID(UUID.randomUUID());
        //         boolean userExist = accountRepository.userExist(user.getBilkentId());

        //         if (userExist) {
        //             usersSet.remove(user);
        //             removedUsers.add(user);
        //         } else {
        //             accountRepository.insertUser(user);
        //         }
        //     } catch (Exception e) {
        //         throw e;
        //     }
        // }

        // try {
            
        //     return true;
        // } catch (Exception e) {

        // }
        // return false;
    }

    private String encodePassword(String plainPassword) {
        try {
            return passwordEncoder.encode(plainPassword);
        } catch (Exception e) {
            throw e;
        }       
    }

    public RUserList getUsers() {
        try {
            return accountRepository.getUsers();
        } catch (Exception e) {
            throw e;
        }
    }
}
