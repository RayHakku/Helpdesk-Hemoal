package com.hemoal.helpdesk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.hemoal.helpdesk.model.Usuario;

/**
 * This interface represents the repository for the Usuario entity.
 */
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {

    /**
     * Finds a Usuario entity by its email.
     *
     * @param email the email of the Usuario to find
     * @return an Optional containing the Usuario entity if found, otherwise empty
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Checks if a Usuario entity exists with the given email.
     *
     * @param email the email to check
     * @return true if a Usuario entity exists with the given email, otherwise false
     */
    Boolean existsByEmail(String email);
}
