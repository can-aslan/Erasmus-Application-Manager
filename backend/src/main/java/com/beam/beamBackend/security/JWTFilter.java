package com.beam.beamBackend.security;

import java.io.IOException;

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
    private final static String tokenBearer = "Bearer";
    @Autowired
    private JWTUserService JWTUserService;
    @Autowired
    private JWTUtils JWTUtils;

    /**
     * @pre make sure that token is not null
     * @param token
     * @return
     * @throws Exception
     */
    public static String getTokenWithoutBearer(String token) throws Exception {
        return token.substring(tokenBearer.length());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println("path: " + request.getServletPath());

        String username = "";
        String token = "";
        String currentEndpoint = request.getServletPath();

        if (tokenHeader == null || !tokenHeader.startsWith(tokenBearer)) {
            System.out.println("no token is found");
            filterChain.doFilter(request, response);
            return;
        }
        
        try { //????
            token = getTokenWithoutBearer(tokenHeader);
        } catch (Exception exc) {
            exc.printStackTrace();
            try {
                throw exc;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean isCurrentRefresh = currentEndpoint.equals("/api/v1/auth/refresh");

        try {
            // set username according to the token type
            if (isCurrentRefresh) {
                username = JWTUtils.extractRefreshUsername(token);
            } else {
                username = JWTUtils.extractAccessUsername(token);
            }            

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails user = JWTUserService.loadUserByUsername(username);
                System.out.println("current refresh: " + currentEndpoint.equals("/api/v1/auth/refresh"));
                if (isCurrentRefresh && JWTUtils.validateRefreshToken(token) || JWTUtils.validateAccessToken(token, user)) {
                    System.out.println("here is here");
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } 
                // else if (JWTUtils.validateAccessToken(token, user)) {
                //     UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                //     authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //     SecurityContextHolder.getContext().setAuthentication(authToken);
                // }
                 else {
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
