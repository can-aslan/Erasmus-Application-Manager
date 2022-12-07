package com.beam.beamBackend.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.beam.beamBackend.model.User;
import com.beam.beamBackend.repository.AccountRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JWTUserService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User appUser = accountRepository.findUserByBilkentId(Long.parseLong(username));
        
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                                    username, 
                                    appUser.getPassword(), 
                                    Collections.singleton(new SimpleGrantedAuthority(appUser.getUserType().toString())));
        return userDetails; 
    }
    
}
