package com.hemoal.helpdesk.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hemoal.helpdesk.services.UserDetailsServicesImplement;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserDetailsServicesImplement userDetailsService;
    @Autowired
    private JwtEntryPoint authEntryPoint;



    @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
        .csrf(csrf -> csrf.disable())
        .exceptionHandling(exceptionHandling -> exceptionHandling
            .authenticationEntryPoint(authEntryPoint))
        .sessionManagement(sessionManagement -> sessionManagement
            .sessionFixation().migrateSession()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/api/auth/**").permitAll()
            //  .requestMatchers("/api/usuario/**").permitAll()
            .anyRequest().authenticated())
            .httpBasic(withDefaults());
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
        }

    @Bean
    public AuthenticationManager authenticationManagerBean(
        AuthenticationConfiguration authenticationConfiguration
      ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
      }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthFilter jwtAuthFilter() {
        return new JWTAuthFilter();
    }

}

