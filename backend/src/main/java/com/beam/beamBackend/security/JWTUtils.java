package com.beam.beamBackend.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTUtils {
    private String accessSingKey = "broccoliisthegreatestfood";
    private String refreshSingKey = "pizzaisalsogood";
    private long accessExp = TimeUnit.HOURS.toMillis(1);
    private long refreshExp = TimeUnit.HOURS.toMillis(24);

    private Claims extractAllClaims(String token, String singKey) throws SignatureException {
        return Jwts.parser().setSigningKey(singKey).parseClaimsJws(token).getBody();
    }

    public Date extractAccessExp(String token) throws SignatureException {
        return extractAllClaims(token, accessSingKey).getExpiration();
    }

    public String extractAccessUsername(String token) throws SignatureException {
        return extractAllClaims(token, accessSingKey).getSubject();
    }

    public boolean isAccessTokenExpired(String token) throws SignatureException {
        return extractAccessExp(token).before(new Date());
    }

    public Date extractRefreshExp(String token) throws SignatureException {
        return extractAllClaims(token, refreshSingKey).getExpiration();
    }

    public String extractRefreshUsername(String token) throws SignatureException {
        return extractAllClaims(token, refreshSingKey).getSubject();
    }

    public boolean isRefreshTokenExpired(String token) throws SignatureException {
        return extractRefreshExp(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, UserDetails user, String singKey, long expiration) {
        long issuedAt = System.currentTimeMillis();

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(issuedAt + expiration))
                .signWith(SignatureAlgorithm.HS256, singKey).compact();
    }

    private String createToken(UserDetails user, String singKey, long expiration) {
        long issuedAt = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder().setClaims(claims)
                .setSubject(user.getUsername())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date(issuedAt))
                .setExpiration(new Date(issuedAt + expiration))
                .signWith(SignatureAlgorithm.HS256, singKey).compact();
    }

    public String createAccessToken(Map<String, Object> claims, UserDetails user) {
        return createToken(claims, user, accessSingKey, accessExp);
    }

    public String createRefreshToken(Map<String, Object> claims, UserDetails user) {
        return createToken(claims, user, refreshSingKey, refreshExp);
    }

    public String createAccessToken(UserDetails user) {
        return createToken(user, accessSingKey, accessExp);
    }

    public String createRefreshToken(UserDetails user) {
        return createToken(user, refreshSingKey, refreshExp);
    }

    public boolean validateAccessToken(String token, UserDetails user) { //better validation?
        String username = extractAccessUsername(token);

        return user.getUsername().equals(username) && !isAccessTokenExpired(token);
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parser().setSigningKey(refreshSingKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            System.out.println("signature exc");
            throw e;
          } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token");
            throw e;
          } catch (ExpiredJwtException e) {
            System.out.println("expired");
            throw e;
          } catch (UnsupportedJwtException e) {
            System.out.println("unsupported");
            throw e;
          } catch (IllegalArgumentException e) {
            System.out.println("illegal args");
            throw e;
          }
        
    }
}
