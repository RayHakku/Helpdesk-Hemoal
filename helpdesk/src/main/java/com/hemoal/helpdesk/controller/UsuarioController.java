package com.hemoal.helpdesk.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemoal.helpdesk.services.UsuarioService;
import com.hemoal.helpdesk.model.Usuario;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public ResponseEntity<List<Usuario>> getUsuario() {
        return usuarioService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable("id")UUID id){
        usuarioService.getUsuarioById(id);
        if(!usuarioService.getUsuarioById(id).isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(usuarioService.getUsuarioById(id).get());
    }

    @DeleteMapping("/{id}")
    public void deleteUsuarioById(@PathVariable("id")UUID id){
        usuarioService.deleteUsuario(id);
    }

    @PutMapping("/{id}")
    public void updateUsuarioById(@PathVariable("id")UUID id, Usuario usuario){
        usuarioService.updateUsuario(id, usuario);
    }

    @GetMapping("/email/{email}")
    public Optional<Usuario> getUsuarioByEmail(@PathVariable("email")String email){
        return usuarioService.getUsuarioByEmail(email);
    }

    @GetMapping("/token/{token}")
    public Optional<Usuario> getUsuarioByToken(@PathVariable("token")String token){
        return usuarioService.getUsuarioByEmailWithToken(token);
    }
    
}
