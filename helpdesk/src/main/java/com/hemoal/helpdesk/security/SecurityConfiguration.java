package com.hemoal.helpdesk.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.hemoal.helpdesk.services.UserDetailsServicesImplement;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

    @Autowired
    private UserDetailsServicesImplement userDetailsService;

    public SecurityConfiguration(UserDetailsServicesImplement userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(requests -> requests
            .requestMatchers(HttpMethod.POST).permitAll()
            .anyRequest().authenticated())
            .httpBasic(withDefaults());
        return http.build();
        }

    // @Bean
    // public AuthenticationManager authenticationManagerBean() throws Exception {
    //     return super.authenticationManagerBean();
    // }
    

}

