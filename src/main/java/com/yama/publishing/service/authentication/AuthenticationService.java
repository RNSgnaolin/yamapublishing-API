package com.yama.publishing.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yama.publishing.domain.user.UserRepository;

import lombok.Getter;

@Service
@Getter
public class AuthenticationService implements UserDetailsService {

    @Autowired
    public AuthenticationService(UserRepository repository) {
        this.repository = repository;
    }

    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
}

