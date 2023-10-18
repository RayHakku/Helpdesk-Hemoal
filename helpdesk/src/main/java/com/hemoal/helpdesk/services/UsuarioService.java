package com.hemoal.helpdesk.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

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

    @Autowired
    EntityManager EntityManager;

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

    /**
     * Returns a list of all usuarios.
     *
     * @return a list of Usuario objects
     */
    public List<Usuario> getAllUsuarios() {
        return usuarioRepo.findAll();
    }

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return An Optional containing the user if found, otherwise an empty Optional.
     */
    public Optional<Usuario> getUsuarioById(UUID id){
        if (!usuarioRepo.existsById(id)) {
            return Optional.empty();
        }
        return usuarioRepo.findById(id);
    }

    /**
     * Deletes a user with the given ID.
     * @param id The ID of the user to be deleted.
     * @return A ResponseEntity with a success message if the user was deleted, or a bad request message if the user was not found.
     */
    @Transactional
    public void deleteUsuario(UUID id) {
        Usuario user = usuarioRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        usuarioRepo.delete(user);
    }
}