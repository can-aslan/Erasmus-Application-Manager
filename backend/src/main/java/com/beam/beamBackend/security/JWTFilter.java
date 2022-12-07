package com.beam.beamBackend.security;

import java.io.IOException;
import java.util.Enumeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.http.HttpHeaders;

import com.beam.beamBackend.service.JWTUserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final String tokenBearer = "Bearer";
    @Autowired
    private JWTUserService JWTUserService;
    @Autowired
    private JWTUtils JWTUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("hello: " + tokenBearer);
        System.out.println("hello: " + tokenHeader);
        String username = "";
        String token = "";

        // HttpServletRequest httpRequest = (HttpServletRequest) request;
        // Enumeration<String> headerNames = httpRequest.getHeaderNames();

        // if (headerNames != null) {
        //         while (headerNames.hasMoreElements()) {
        //                 System.out.println("Header: " + headerNames.nextElement());
        //                 System.out.println("Value: " + httpRequest.getHeader(headerNames.nextElement()));
        //         }
        // }

        if (tokenHeader == null) {
            System.out.println("token is null");
        } else {
            System.out.println("starts with: " + tokenHeader.startsWith(tokenBearer));
        }
        // if (tokenHeader.startsWith(tokenBearer)) {
        //     System.out.println("token no start with beraer");
        // }

        // System.out.println("starts with: " + tokenHeader.startsWith(tokenBearer));

        if (tokenHeader == null || !tokenHeader.startsWith(tokenBearer)) {
            System.out.println("no token is found in hereeee");
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("starts with: " + tokenHeader.startsWith(tokenBearer));

        System.out.println("beraer: " + tokenBearer);
        System.out.println("length: " + tokenBearer.length());
        
        token = tokenHeader.substring(tokenBearer.length());

        try {
            username = JWTUtils.extractUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = JWTUserService.loadUserByUsername(username);
    
                if (JWTUtils.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("token is not valid");
                }
            } else {
                System.out.println("username is not valid");
            }
    
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            System.out.println("JWT token cannot be verified");
            throw e;
        } catch (IllegalArgumentException e) {
            System.out.println("Unable to get JWT Token");
            throw e;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT Token has expired");
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
    
}
