package com.beam.beamBackend.service;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.model.UserLogin;
import com.beam.beamBackend.repository.IAccountRepository;
import com.beam.beamBackend.request.ChangePassword;
import com.beam.beamBackend.response.RLoginUser;
import com.beam.beamBackend.response.RRefreshToken;
import com.beam.beamBackend.response.RUserList;
import com.beam.beamBackend.response.ResponseId;
import com.beam.beamBackend.security.JWTFilter;
import com.beam.beamBackend.security.JWTUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {
    public static int hashStrength = 10;

    @Autowired
    final BCryptPasswordEncoder bCryptPasswordEncoder;

    // @Autowired
    private final IAccountRepository accountRepository;

    @Autowired
    private final JWTUserService jwtUserService;

    @Autowired
    private final JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authManager;

    public RLoginUser login(UserLogin user) throws Exception {
        try {
            System.out.println("id: "+ user.getBilkentId());
            User dbUser = accountRepository.findUserByBilkentId(Long.parseLong(user.getBilkentId()));

            System.out.println("user: " + dbUser);
            if (dbUser == null) {
                throw new Exception("user is not found");
            }
            
            String hashedPassword = dbUser.getPassword();
            //String hashedPassword = accountRepository.getPasswordIfUserExist(Long.parseLong(user.getBilkentId()));
            boolean passwordMatch = bCryptPasswordEncoder.matches(user.getPassword(), hashedPassword);

            if (!passwordMatch) {
                throw new Exception("passwords do not match");
            }

            System.out.println("passwords are matched");

            // authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getBilkentId(), user.getPassword()));
            // User appUser = accountRepository.findUserByBilkentId(Long.parseLong(user.getBilkentId()));
            final UserDetails userDetails = jwtUserService.loadUserByUsername(user.getBilkentId());
            final String accessToken = jwtUtils.createAccessToken(userDetails);
            final String refreshToken = jwtUtils.createRefreshToken(userDetails);
            return new RLoginUser(dbUser, accessToken, refreshToken);
        } catch (Exception e) {
            System.out.println("login exception");
            e.printStackTrace();
            throw e;
        }
    }

    public RRefreshToken refreshToken(String auth) throws Exception {
        try {
            String username = jwtUtils.extractRefreshUsername(JWTFilter.getTokenWithoutBearer(auth));

            // authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getBilkentId(), user.getPassword()));

            final UserDetails userDetails = jwtUserService.loadUserByUsername(username);
            final String accessToken = jwtUtils.createAccessToken(userDetails);
            return new RRefreshToken(accessToken);
            
        } catch (Exception e) {
            System.out.println("refresh token exception");
            e.printStackTrace();
            throw e;
        }
    }

    public boolean changePassword(String auth, ChangePassword passwords) throws Exception {
        try {
            String username = jwtUtils.extractAccessUsername(JWTFilter.getTokenWithoutBearer(auth));
            User dbUser = accountRepository.findUserByBilkentId(Long.parseLong(username));

            if (dbUser == null) {
                throw new Exception("user is not found");
            }
            
            String hashedPassword = dbUser.getPassword();
            // username is bilkentId in this context
            
            
            // String hashedPassword = accountRepository.getPasswordIfUserExist(Long.parseLong(username));
            boolean passwordMatch = bCryptPasswordEncoder.matches(passwords.getOldPassword(), hashedPassword);

            if (!passwordMatch) {
                System.out.println("passwords does not match");
                throw new Exception("pasword no match");
            }

            System.out.println("passwords are matched");
            // hash new password
            String hashedNewPassword = bCryptPasswordEncoder.encode(passwords.getNewPassword());
            
            dbUser.setPassword(hashedNewPassword);
            int result = accountRepository.updatePassword(hashedNewPassword, dbUser.getId());
            // accountRepository.deleteById(dbUser.getId());
            // accountRepository.save(dbUser);
            System.out.println("result: " + result);
            return true;
            
        } catch (Exception e) {
            System.out.println("password change exception exception");
            e.printStackTrace();
            throw e;
        }
    }

    public UUID addUser(User user) throws Exception {
        try {
            boolean userExist = accountRepository.existsByBilkentId(user.getBilkentId());
            
            if (userExist) {
                throw new Exception();
            } else {
                // generate uuid and hash password if user does not exist in the system
                user.setId(UUID.randomUUID());
                // user.setPassword(user.getPassword());
                user.setPassword(encodePassword(user.getPassword()));

                accountRepository.save(user);
                return user.getId();
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public HashSet<User> addUserChunk(User[] users) throws Exception {
        HashSet<User> usersSet = new HashSet<>(Arrays.asList(users));
        HashSet<User> removedUsers = new HashSet<>();

        for (User user : usersSet) {
            try {
                boolean userExist = accountRepository.existsByBilkentId(user.getBilkentId());

                if (userExist) {
                    usersSet.remove(user);
                    removedUsers.add(user);
                } else {
                    user.setId(UUID.randomUUID());
                    // user.setPassword(user.getPassword());
                    user.setPassword(encodePassword(user.getPassword()));
                }
            } catch (Exception e) {
                throw e;
            }
        }

        try {
            accountRepository.saveAll(usersSet);
        } catch (Exception e) {
            System.out.println("users not saved: " + removedUsers);
            throw e;
        }

        return usersSet;
    }

    private String encodePassword(String plainPassword) {
        try {
            return bCryptPasswordEncoder.encode(plainPassword);
        } catch (Exception e) {
            throw e;
        }       
    }

    public List<User> getUsers() {
        try {
            return accountRepository.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}