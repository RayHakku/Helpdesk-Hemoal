package com.hemoal.helpdesk.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hemoal.helpdesk.model.Chamados;
import com.hemoal.helpdesk.services.ChamadosServices;

@RestController
@RequestMapping("api/chamados")
public class ChamadosController {
    @Autowired
    ChamadosServices chamadosServices;

    @PostMapping("/criar")
    public ResponseEntity<String> createTicket(@RequestHeader("Authorization")String jwtToken,@RequestBody Chamados chamado){
        return chamadosServices.criarChamado(chamado, jwtToken);
    }

    @GetMapping("/listar")
    public List<Chamados> listTickets(){
        return chamadosServices.listarChamados();
    }

    @GetMapping("/{id}")
    public Optional<Chamados> getTicketById(@PathVariable("id")Long id){
        return chamadosServices.pegarChamadoId(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTickerById(@PathVariable("id")Long id){
        return chamadosServices.deletarChamado(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTicker(@PathVariable("id")Long id, @RequestBody Chamados chamado){
        return chamadosServices.atualizarChamado(id, chamado);
    }

    @PatchMapping("/{id}")
    public void updateStatusTicket(@PathVariable("id")Long id, @RequestBody String nome){
        chamadosServices.atulizarStatusChamado(id, nome);
    }


    

}
