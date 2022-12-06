package com.beam.beamBackend.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.response.RUserList;
import com.beam.beamBackend.response.ResponseId;

@Qualifier("account")
@Repository
public class AccountRepository {
    private static List<User> DB = new ArrayList<>();

    public ResponseId insertUser(User user) {
        System.out.println("tset we are in repo");
        DB.add(user);
        System.out.println("user id:" + user.getId());
        return new ResponseId(user.getId(), user.getBilkentId()); 
    }

    public boolean userExist(long bilkentId) {
        System.out.println("in account repo user exist");
        return DB.stream().filter(user -> user.getBilkentId() == bilkentId).findFirst().isPresent();
    }

    public String getPasswordIfUserExist(long bilkentId) {
        System.out.println("in account repo user exist paswor");
        User user = DB.stream().filter(u -> u.getBilkentId() == bilkentId).findFirst().get();

        if (user != null) {
            return user.getPassword();
        } else {
            throw new Error("no user found");
        }
    }

    public RUserList getUsers() {
        return new RUserList(DB.size(), DB);
    }
}
