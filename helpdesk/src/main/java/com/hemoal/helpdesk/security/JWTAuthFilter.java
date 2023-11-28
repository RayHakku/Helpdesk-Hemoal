package com.hemoal.helpdesk.security;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hemoal.helpdesk.services.UserDetailsServicesImplement;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private UserDetailsServicesImplement userDatailsServicesImplement;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
                try {
                   String token = getJWTFromRequest(request);
        if(StringUtils.hasText(token) && jwtGenerator.validateToken(token)){
            String[] subject = jwtGenerator.getUsernameFromJWT(token).split(", ");
            String username = subject[0];
            System.out.println("Auth Filter Username: " + username);
            var userDetails = userDatailsServicesImplement.loadUserByUsername(username);

            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response); 
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
    
}
