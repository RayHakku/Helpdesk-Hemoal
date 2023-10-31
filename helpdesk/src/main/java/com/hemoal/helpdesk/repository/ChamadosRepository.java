package com.hemoal.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hemoal.helpdesk.model.Chamados;

public interface ChamadosRepository extends JpaRepository<Chamados, Long> {

}
