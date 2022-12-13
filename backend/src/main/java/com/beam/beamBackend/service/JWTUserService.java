package com.beam.beamBackend.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.IAccountRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JWTUserService implements UserDetailsService {
    private final IAccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User appUser = accountRepository.findUserByBilkentId(Long.parseLong(username));
    
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                                        username, 
                                        appUser.getPassword(), 
                                        Collections.singleton(new SimpleGrantedAuthority(appUser.getUserType().toString())));
            return userDetails; 
        } catch (Exception e) {
            throw e;
        }
    }

    public UserDetails loadUserByUsername(User appUser) throws UsernameNotFoundException {
        try {
        
            UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                                        appUser.getBilkentId() + "", 
                                        appUser.getPassword(), 
                                        Collections.singleton(new SimpleGrantedAuthority(appUser.getUserType().toString())));
            return userDetails; 
        } catch (Exception e) {
            throw e;
        }
    }
    
}
