package com.hemoal.helpdesk.security;

import org.springframework.stereotype.Component;

import com.hemoal.helpdesk.repository.UsuarioRepo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;



@Component
public class JWTGenerator {
    @Autowired
    UsuarioRepo userRepository;

    public static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        String userID = userRepository.findByEmail(username).get().getId().toString();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + SecurityConstants.JWT_Expire_Time);

        System.out.println("ID: " + userID);

        String token = Jwts.builder()
        .setSubject(String.format("%s, %s", username, userID))
        .setIssuedAt(currentDate)
        .setExpiration(expirationDate)
        .signWith(key, SignatureAlgorithm.HS512)
        .compact();

        System.out.println("Token: " + token);
        return token;
    }

    public String getUsernameFromJWT(String token){
        Claims claims = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Token Invalido",e.fillInStackTrace());
        }
    }
}