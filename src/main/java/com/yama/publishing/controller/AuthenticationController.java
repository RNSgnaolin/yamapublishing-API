package com.yama.publishing.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yama.publishing.domain.user.DataUser;
import com.yama.publishing.domain.user.User;
import com.yama.publishing.infra.security.DataTokenJWT;
import com.yama.publishing.service.authentication.TokenService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/login")
public class AuthenticationController {

    private AuthenticationManager manager;
    private TokenService tokenService;

    AuthenticationController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<DataTokenJWT> validateLogin(@RequestBody @Valid DataUser data) {

        var authenticationToken = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = manager.authenticate(authenticationToken);
        User user = (User) authentication.getPrincipal();
        var tokenJWT = tokenService.generateToken(user);

        return ResponseEntity.ok(new DataTokenJWT(tokenJWT));
        
    }
    
}
