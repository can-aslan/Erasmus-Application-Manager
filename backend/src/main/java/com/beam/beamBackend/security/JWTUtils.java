package com.beam.beamBackend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JWTUtils {
    private String singKey = "broccoliisthegreatestfood";

    private Claims extractAllClaims(String token) throws SignatureException {
        return Jwts.parser().setSigningKey(singKey).parseClaimsJws(token).getBody();
    }

    public Date extractExp(String token) throws SignatureException {
        return extractAllClaims(token).getExpiration();
    }

    public String extractUsername(String token) throws SignatureException {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) throws SignatureException {
        return extractExp(token).before(new Date());
    }

    public String createToken(Map<String, Object> claims, UserDetails user) {
        long issuedAt = System.currentTimeMillis();

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(issuedAt + TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS256, singKey).compact();
    }

    public String createToken(UserDetails user) {
        long issuedAt = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(issuedAt + TimeUnit.HOURS.toMillis(1)))
                .signWith(SignatureAlgorithm.HS256, singKey).compact();
    }

    public boolean validateToken(String token, UserDetails user) {
        String username = extractUsername(token);

        return user.getUsername().equals(username) && !isTokenExpired(token);
    }
}
