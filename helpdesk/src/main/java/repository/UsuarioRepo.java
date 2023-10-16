package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hemoal.helpdesk.model.Usuario;

interface UsuarioRepo extends JpaRepository<Usuario, Long> {
    
}
