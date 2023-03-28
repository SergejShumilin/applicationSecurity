package com.example.service;

import com.example.dao.UserRepository;
import com.example.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private LoginAttemptService loginAttemptService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUserName(username);

        if (user == null){
            throw new UsernameNotFoundException("User doesn't exist");
        } else {
            if (loginAttemptService.isBlocked(username)) {
                throw new LockedException("User is blocked");
            }
        }

        String[] authorities = user.getUserAuthorities().split(";");

        return User.withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

    }
}
