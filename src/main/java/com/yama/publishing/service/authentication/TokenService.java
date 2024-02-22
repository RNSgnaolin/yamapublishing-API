package com.yama.publishing.service.authentication;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yama.publishing.domain.user.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {

        String token;

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withIssuer("Yama Publishing API")
                    .withSubject(user.getLogin())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Error generating token", null);
        }

        return token;
    }

    public String getSubject(String tokenJWT) {

        try {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm)
        .withIssuer("Yama Publishing API")
        .build()
        .verify(tokenJWT)
        .getSubject();
        } catch (JWTVerificationException e) {
            throw new SecurityException("Invalid or expired token");
        }

    }

    private Instant expirationDate() {
        return Instant.now().plusSeconds(7200); // 2 hours expiration
    }

}
