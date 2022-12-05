package com.beam.beamBackend.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean addUser(User user) {
        try {
            user.setUUID(UUID.randomUUID());
            boolean userExist = accountRepository.userExist(user.getBilkentId());

            if (userExist) {
                return false;
            } else {
                return accountRepository.insertUser(user);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public boolean addUserChunk(User[] users) {
        HashSet<User> usersSet = new HashSet<>(Arrays.asList(users));

        for (User user : usersSet) {
            try {
                user.setUUID(UUID.randomUUID());
                boolean userExist = accountRepository.userExist(user.getBilkentId());

                if (userExist) {
                    usersSet.remove(user);
                }
            } catch (Exception e) {
                throw e;
            }
        }

        try {
            return true;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return false;
    }
}
