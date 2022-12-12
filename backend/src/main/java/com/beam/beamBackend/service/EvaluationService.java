package com.beam.beamBackend.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.UniEvaluationForm;
import com.beam.beamBackend.repository.EvaluationRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class EvaluationService {

    private final EvaluationRepository evalRepository;

    public UUID evaluateUni(UniEvaluationForm uniEval) throws Exception {
        try {
            uniEval.setId(UUID.randomUUID());
            Boolean result = evalRepository.save(uniEval);
            return uniEval.getUniId();
            
        } catch (Exception e) {
            System.out.println("course eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    public List<UniEvaluationForm> getUniEval(UUID uniId) throws Exception {
        try {
            List<UniEvaluationForm> result = evalRepository.findByUniId(uniId);
            return result;            
        } catch (Exception e) {
            System.out.println("cannot get eval exception");
            e.printStackTrace();
            throw e;
        }
    }

    // public RRefreshToken refreshToken(String auth) throws Exception {
    //     try {
    //         String username = jwtUtils.extractRefreshUsername(JWTFilter.getTokenWithoutBearer(auth));

    //         // authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getBilkentId(), user.getPassword()));

    //         final UserDetails userDetails = jwtUserService.loadUserByUsername(username);
    //         final String accessToken = jwtUtils.createAccessToken(userDetails);
    //         return new RRefreshToken(accessToken);
            
    //     } catch (Exception e) {
    //         System.out.println("refresh token exception");
    //         e.printStackTrace();
    //         throw e;
    //     }
    // }

    // public boolean changePassword(String auth, ChangePassword passwords) throws Exception {
    //     try {
    //         // username is bilkentId in this context
    //         String username = jwtUtils.extractAccessUsername(JWTFilter.getTokenWithoutBearer(auth));
            
    //         String hashedPassword = accountRepository.getPasswordIfUserExist(Long.parseLong(username));
    //         boolean passwordMatch = passwordEncoder.matches(passwords.getOldPassword(), hashedPassword);

    //         if (!passwordMatch) {
    //             System.out.println("passwords does not match");
    //             throw new Exception();
    //         } else {
    //             System.out.println("passwords are matched");
    //             // hash new password
    //             String hashedNewPassword = passwordEncoder.encode(passwords.getNewPassword());

    //             return accountRepository.editPasswordByBilkentId(Long.parseLong(username), hashedNewPassword);
    //         }
            
    //     } catch (Exception e) {
    //         System.out.println("password change exception exception");
    //         e.printStackTrace();
    //         throw e;
    //     }
    // }

    // public ResponseId addUser(User user) throws Exception {
    //     try {
    //         boolean userExist = accountRepository.userExist(user.getBilkentId());
            
    //         if (userExist) {
    //             throw new Exception();
    //         } else {
    //             // generate uuid and hash password if user does not exist in the system
    //             user.setUUID(UUID.randomUUID());
    //             user.setPassword(encodePassword(user.getPassword()));

    //             return accountRepository.insertUser(user);
    //         }
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }

    // public boolean addUserChunk(User[] users) throws Exception {
    //     // HashSet<User> usersSet = new HashSet<>(Arrays.asList(users));
    //     // HashSet<User> removedUsers = new HashSet<>();

    //     for (User user : users) {
    //         try {
    //             addUser(user);
    //         } catch (Exception e) {
    //             throw e;
    //         }
    //     }

    //     return true;

    //     // for (User user : usersSet) {
    //     //     try {
    //     //         user.setUUID(UUID.randomUUID());
    //     //         boolean userExist = accountRepository.userExist(user.getBilkentId());

    //     //         if (userExist) {
    //     //             usersSet.remove(user);
    //     //             removedUsers.add(user);
    //     //         } else {
    //     //             accountRepository.insertUser(user);
    //     //         }
    //     //     } catch (Exception e) {
    //     //         throw e;
    //     //     }
    //     // }

    //     // try {
            
    //     //     return true;
    //     // } catch (Exception e) {

    //     // }
    //     // return false;
    // }

    // private String encodePassword(String plainPassword) {
    //     try {
    //         return passwordEncoder.encode(plainPassword);
    //     } catch (Exception e) {
    //         throw e;
    //     }       
    // }

    // public RUserList getUsers() {
    //     try {
    //         return accountRepository.getUsers();
    //     } catch (Exception e) {
    //         throw e;
    //     }
    // }
}
