package com.hemoal.helpdesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemoal.helpdesk.DTO.AuthResponseDTO;
import com.hemoal.helpdesk.model.Usuario;
import com.hemoal.helpdesk.services.UsuarioService;


@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/registrar")
    public ResponseEntity<String> registroUsuario(@RequestBody Usuario usuario) {
        return usuarioService.registrarUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> loginUsuario(@RequestBody Usuario usuario){
        return usuarioService.loginUsuario(usuario);
    }
}
