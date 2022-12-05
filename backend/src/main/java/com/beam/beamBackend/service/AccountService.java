package com.beam.beamBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.User;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(@Qualifier("list") AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean addUser(User user) {
        try {
            boolean userExist = accountRepository.userExist(long bilkentId);

            if (userExist) {
                return false;
            } else {
                accountRepository.insertUser(user);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
