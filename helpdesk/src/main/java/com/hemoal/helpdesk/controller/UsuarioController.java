package com.hemoal.helpdesk.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<Usuario> getUsuario() {
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
    
}
