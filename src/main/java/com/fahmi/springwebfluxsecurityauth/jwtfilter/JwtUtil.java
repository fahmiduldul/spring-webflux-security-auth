package com.fahmi.springwebfluxsecurityauth.jwtfilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Slf4j
@Component
@NoArgsConstructor
public class JwtUtil {
    // create random set of bit for secret
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final int expirationTime = 100000000;

    public String encode(String subject){

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + (long) this.expirationTime))
                .signWith(this.secretKey)
                .compact();
    }

    public JwtParser getParser(){
        return Jwts.parserBuilder().setSigningKey(this.secretKey).build();
    }

    public Claims getAllClaims(String jws){
        return this.getParser().parseClaimsJws(jws).getBody();
    }

    public String getSubject(String jws){
        return this.getAllClaims(jws).getSubject();
    }

    public boolean verify(String jws){
        return this.getAllClaims(jws).getExpiration().before(new Date());
    }
}
