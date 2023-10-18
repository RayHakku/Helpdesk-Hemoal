package com.hemoal.helpdesk.services;

import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hemoal.helpdesk.model.Role;
import com.hemoal.helpdesk.model.Usuario;
import com.hemoal.helpdesk.repository.RoleRepository;
import com.hemoal.helpdesk.repository.UsuarioRepo;

/**
 * This class represents the service layer for the Usuario entity.
 */
@Service
public class UsuarioService {

    @Autowired
    UsuarioRepo usuarioRepo;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    /**
     * This method registers a new user in the system.
     * @param usuario The user to be registered.
     * @return A ResponseEntity with a message indicating if the registration was successful or not.
     */
    public ResponseEntity<String> registrarUsuario(Usuario usuario) {
        
        if(usuarioRepo.existsByEmail(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("Usuario ja Existe");
        }
        
        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
        
        Role roles = roleRepository.findByRole("USER").orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        usuario.setRoles(Collections.singletonList(roles));

        usuarioRepo.save(usuario);
        
        return ResponseEntity.ok().body("Usuario Registrado");
    }

    /**
     * Authenticates the user with the given email and password.
     * 
     * @param usuario The user object containing email and password.
     * @return A ResponseEntity with a success message if authentication is successful, or a bad request message if authentication fails.
     */
    public ResponseEntity<String> loginUsuario(Usuario usuario) {
        try{
            // 1. Authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getPassword())
            );
            // 2. Set the Authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 3. Create token
            return ResponseEntity.ok().body("Usuario Logado");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("Email ou Senha Invalidos");
        }
    }
}
