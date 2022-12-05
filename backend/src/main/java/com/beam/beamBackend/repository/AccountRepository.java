package com.beam.beamBackend.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.beam.beamBackend.model.User;

@Qualifier("account")
@Repository
public class AccountRepository {
    private static List<User> DB = new ArrayList<>();

    public boolean insertUser(User user) {
        return DB.add(user);
    }

    public boolean userExist(long bilkentId) {
        return DB.stream().filter(user -> user.getBilkentId() == bilkentId).findFirst() != null;
    }

}
