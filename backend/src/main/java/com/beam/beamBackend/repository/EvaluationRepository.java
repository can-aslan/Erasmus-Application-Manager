package com.beam.beamBackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.UniEvaluationForm;

@Qualifier("evaluation")
@Repository
public class EvaluationRepository {
    private static List<UniEvaluationForm> DB = new ArrayList<>();

    public Boolean save(UniEvaluationForm uniEval) {
        // System.out.println("tset we are in repo");
        
        System.out.println("user id:" + uniEval.getUniId());
        System.out.println("user id:" + uniEval);
        return DB.add(uniEval); 
    }

    public List<UniEvaluationForm> findAll() {
        return DB;
    }

    public List<UniEvaluationForm> findByUniId(UUID uniId) {
        Optional<UniEvaluationForm> form = DB.stream()
                    .filter(uni -> uni.getUniId()== uniId)
                    .findFirst();
                    System.out.println(form);
        System.out.println("hello in: " + uniId);
        return DB.stream()
                    .filter(uni -> uni.getUniId().equals(uniId))
                    .toList();
    }

    // public boolean userExist(long bilkentId) {
    //     System.out.println("in account repo user exist");
    //     return DB.stream().filter(user -> user.getBilkentId() == bilkentId).findFirst().isPresent();
    // }

    // public String getPasswordIfUserExist(long bilkentId) {
    //     System.out.println("in account repo user exist paswor");
    //     User user = DB.stream().filter(u -> u.getBilkentId() == bilkentId).findFirst().get();

    //     if (user != null) {
    //         return user.getPassword();
    //     } else {
    //         throw new Error("no user found");
    //     }
    // }

    // public RUserList getUsers() {
    //     return new RUserList(DB.size(), DB);
    // }

    // public User findUserByBilkentId(long bilkentId) {
    //     return DB.stream()
    //                 .filter(u -> u.getBilkentId() == bilkentId)
    //                 .findFirst()
    //                 .orElseThrow(() -> new UsernameNotFoundException("no user was found with bilkent id " + bilkentId));
    // }

    // public boolean editPasswordByBilkentId(long bilkentId, String newPassword) {
    //     User user = DB.stream()
    //             .filter(u -> u.getBilkentId() == bilkentId)
    //             .findFirst()
    //             .orElseThrow(() -> new UsernameNotFoundException("no user was found with bilkent id " + bilkentId));

    //     user.setPassword(newPassword);

    //     user = DB.stream()
    //             .filter(u -> u.getBilkentId() == bilkentId)
    //             .findFirst()
    //             .orElseThrow(() -> new UsernameNotFoundException("no user was found with bilkent id " + bilkentId));
    //     System.out.println("user password: " + user.getPassword());
    //     return true;
    // }
}
