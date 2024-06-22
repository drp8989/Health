package com.JustHealth.Health.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Service
public class JwtProvider {

    private final SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

//    private final SecretKey key=Keys.builder(Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes())).build();

    public String generateToken(Authentication auth){

        Collection<? extends GrantedAuthority> authorities=auth.getAuthorities();
        System.out.println(authorities+"from generate token");
        String roles=populateAuthorities(authorities);
        System.out.println(roles+"from authorities");

        String jwt= Jwts.builder().issuedAt(new Date()).expiration(new Date(new Date().getTime()+86400000))
                .claim("email",auth.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();

        return jwt;

    }
    public String getEmailFromJwtToken(String jwt){
        jwt=jwt.substring(7);
        Claims claims=Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();



        return String.valueOf(claims.get("email"));
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
        System.out.println(authorities+"from populate authorities");
        Set<String> auths=new HashSet<>();

        for (GrantedAuthority authority:authorities){
            auths.add(authority.getAuthority());

        }

        return String.join(",",auths);

    }
}
