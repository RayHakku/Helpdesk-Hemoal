package com.hemoal.helpdesk.services;


import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hemoal.helpdesk.model.Role;
import com.hemoal.helpdesk.model.Usuario;
import com.hemoal.helpdesk.repository.UsuarioRepo;

@Service
public class UserDetailsServicesImplement implements UserDetailsService {
    @Autowired
    private UsuarioRepo usuarioRepo;

    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepo.findByEmail(email)
        .orElseThrow(()-> new UsernameNotFoundException("Email nao encontrado"));
        return new User(usuario.getEmail(), usuario.getPassword(), getAuthorities(usuario.getRoles()));
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRole())).toList();
    }

    
}