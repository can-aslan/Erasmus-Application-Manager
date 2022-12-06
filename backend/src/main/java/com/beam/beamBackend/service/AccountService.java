package com.beam.beamBackend.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.AccountRepository;
import com.beam.beamBackend.response.RUserList;
import com.beam.beamBackend.response.ResponseId;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public ResponseId addUser(User user) throws Exception {
        try {
            user.setUUID(UUID.randomUUID());
            boolean userExist = accountRepository.userExist(user.getBilkentId());
            
            if (userExist) {
                throw new Exception();
            } else {
                System.out.println("no errror");
                return accountRepository.insertUser(user);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean addUserChunk(User[] users) {
        HashSet<User> usersSet = new HashSet<>(Arrays.asList(users));
        HashSet<User> removedUsers = new HashSet<>();

        for (User user : usersSet) {
            try {
                user.setUUID(UUID.randomUUID());
                boolean userExist = accountRepository.userExist(user.getBilkentId());

                if (userExist) {
                    usersSet.remove(user);
                    removedUsers.add(user);
                } else {
                    accountRepository.insertUser(user);
                }
            } catch (Exception e) {
                throw e;
            }
        }

        try {
            
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    public RUserList getUsers() {
        try {
            return accountRepository.getUsers();
        } catch (Exception e) {
            throw e;
        }
    }
}
