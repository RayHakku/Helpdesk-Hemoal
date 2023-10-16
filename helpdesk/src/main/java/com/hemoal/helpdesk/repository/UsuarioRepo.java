package com.hemoal.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hemoal.helpdesk.model.Usuario;

public interface UsuarioRepo extends JpaRepository<Usuario, Long> {
    
}
