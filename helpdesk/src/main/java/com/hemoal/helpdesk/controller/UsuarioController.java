package com.hemoal.helpdesk.controller;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hemoal.helpdesk.model.Usuario;
import com.hemoal.helpdesk.repository.UsuarioRepo;


@RestController
@RequestMapping("/api")
public class UsuarioController {
    @Autowired
    UsuarioRepo ur;

    @GetMapping("/usuarios")
    public List<Usuario> getUsuarios() {
        return ur.findAll();
    }

    @GetMapping("/usuarios/{id}")
    public Optional<Usuario> getUsuarioById(@PathVariable(value = "id") Long id) {
        return ur.findById(id);
    }

    @PostMapping("/usuarios")
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return ur.save(usuario);
    }

    @DeleteMapping("/usuarios/{id}")
    public void deleteUsuario(@PathVariable(value = "id") Long id) {
        ur.deleteById(id);
    }

    @PutMapping("/usuarios/{id}")
    public Usuario updateUsuario(@PathVariable(value = "id") Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> u = ur.findById(id);
        Usuario _u = u.get();
        _u.setEmail(usuario.getEmail());
        _u.setPassword(usuario.getPassword());
        _u.setRole(usuario.getRole());
        return ur.save(_u);
    }
}
