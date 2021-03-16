package com.springredditclone.redditclone.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service

public class JwtProvider  {

    @Value("{auth.secret}")
    private String TOKEN_SECRET;

    public String generateToken(Authentication authentication){
        User principle =(User)authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principle.getUsername())
                .signWith(SignatureAlgorithm.HS512,TOKEN_SECRET)
                .compact();
    }
}
