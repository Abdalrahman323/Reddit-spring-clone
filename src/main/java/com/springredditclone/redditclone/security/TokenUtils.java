package com.springredditclone.redditclone.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class TokenUtils {

    @Value("{auth.secret}")
    private String TOKEN_SECRET;

    public String generateToken(Authentication authentication){
        User principle =(User)authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(principle.getUsername())
                .signWith(SignatureAlgorithm.HS512,TOKEN_SECRET)
                .compact();
    }


    public boolean validateToken(String jwt){
        try {
            Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(jwt);

        } catch (Exception e){
            throw e;
        }

        return true;
    }

    public String getUserNameFromToken(String token) {

        try {
            return getClaims(token).getSubject();

        }catch (Exception ex){
            return  null;
        }


    }

    private Claims getClaims(String token) {
        Claims claims;

        try {
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

        } catch (Exception ex) {
            claims =  null;
        }

        return  claims;
    }
}